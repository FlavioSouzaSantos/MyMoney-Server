package br.com.mymoney.crudcommon.services;

import br.com.mymoney.crudcommon.exceptions.ResponseErrorException;
import br.com.mymoney.crudcommon.exceptions.ValidationException;
import br.com.mymoney.crudcommon.models.dtos.ResponseErrorDto;
import br.com.mymoney.crudcommon.models.dtos.ResponsePageDto;
import br.com.mymoney.crudcommon.utils.JWTUtil;
import br.com.mymoney.crudcommon.models.entities.BaseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Log
@RequiredArgsConstructor
public abstract class CrudService<T extends BaseEntity<ID>, ID> {
    public static final String BEARER="Bearer";
    public static final String EMPYT = "";

    @Autowired protected JpaRepository<T,ID> repository;
    @Autowired protected Validator validator;
    @Autowired protected ObjectMapper objectMapper;
    @Autowired protected MessageSource messageSource;
    @Value("${security.jwt.secret:}")
    protected String secret;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Optional<T> create(Optional<T> entity, HttpServletRequest request) {
        if(entity.isPresent()){
            setDefaultValues(entity, request);
            return Optional.ofNullable(repository.save(entity.get()));
        }else return Optional.empty();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Optional<T> fullUpdate(Optional<T> entity, Optional<ID> id, HttpServletRequest request) {
        if(entity.isPresent() && id.isPresent() && repository.existsById(id.get())){
            entity.get().setId(id.get());
            setDefaultValues(entity, request);

            DataBinder binder = new DataBinder(entity.get());
            BindingResult bindingResult = binder.getBindingResult();
            validator.validate(entity.get(), bindingResult);
            if(bindingResult.hasErrors()){
                Set<ResponseErrorDto> errors = bindingResult.getFieldErrors().stream()
                        .map(ResponseErrorDto::new)
                        .collect(Collectors.toSet());

                throw new ResponseErrorException(errors);
            }

            return Optional.ofNullable(repository.save(entity.get()));
        }
        return Optional.empty();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Optional<T> partialUpdate(InputStream jsonBody, Optional<ID> id, HttpServletRequest request) {
        if(jsonBody != null && id.isPresent() && repository.existsById(id.get())){
            try{
                Optional<T> tempEntity = repository.findById(id.get());
                setDefaultValues(tempEntity, request);

                ObjectReader objectReader = objectMapper.readerForUpdating(tempEntity.get());
                T entity = (T) objectReader.readValue(jsonBody);

                DataBinder binder = new DataBinder(entity);
                BindingResult bindingResult = binder.getBindingResult();
                validator.validate(entity, bindingResult);
                if(bindingResult.hasErrors()){
                    Set<ResponseErrorDto> errors = bindingResult.getFieldErrors().stream()
                            .map(ResponseErrorDto::new)
                            .collect(Collectors.toSet());

                    throw new ResponseErrorException(errors);
                }

                return Optional.ofNullable(repository.save(entity));
            }catch (IOException ex){
                log.log(Level.SEVERE, ex.getMessage(), ex);
                throw new ResponseErrorException(Set.of(new ResponseErrorDto(messageSource.getMessage("error.deserialization.json", null, null))));
            }
        }
        return Optional.empty();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean delete(Optional<ID> id) {
        if(id.isPresent() && repository.existsById(id.get())){
            Optional<T> entity = repository.findById(id.get());
            validateDeleteEntity(entity);
            repository.delete(entity.get());
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Optional<T> findById(Optional<ID> id){
        if(id.isPresent() && repository.existsById(id.get())){
            return repository.findById(id.get());
        }else return Optional.empty();
    }

    @Transactional(readOnly = true)
    public Optional<ResponsePageDto<T>> findAll(Specification<T> specification,
                                                int page, int size, String[] order, String direction) {
        PageRequest pageRequest;
        if(order != null && order.length > 0){
            pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), order);
        }else{
            pageRequest = PageRequest.of(page, size);
        }

        Page<T> pageResult;
        if(specification != null && repository instanceof JpaSpecificationExecutor){
            pageResult = ((JpaSpecificationExecutor) repository).findAll(specification, pageRequest);
        }else{
            pageResult = repository.findAll(pageRequest);
        }

        return Optional.of(new ResponsePageDto(pageResult));
    }

    /**
     * Seta os valores padrão na entidade antes de ser salva.
     * @param entity Entidade a ser salva
     */
    protected void setDefaultValues(Optional<T> entity, HttpServletRequest request){}

    /**
     * Valida se a entidade pode ser excluída.
     * @param entity
     * @throws ValidationException
     */
    protected void validateDeleteEntity(Optional<T> entity) throws ValidationException {}

    public Optional<UUID> getUUIDAuthenticated(HttpServletRequest request){
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorization != null && !authorization.isBlank() && authorization.startsWith(BEARER)){
            return JWTUtil.getSubject(authorization.replace(BEARER, EMPYT), secret)
                    .map(p -> Optional.of(UUID.fromString(p)))
                    .orElse(Optional.empty());
        } else return Optional.empty();
    }
}
