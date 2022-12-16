package br.com.mymoney.cadastrationservice.controllers;

import br.com.mymoney.cadastrationservice.exceptions.ResponseErrorException;
import br.com.mymoney.cadastrationservice.models.dtos.ResponseErrorDto;
import br.com.mymoney.cadastrationservice.models.dtos.ResponsePageDto;
import br.com.mymoney.cadastrationservice.models.entities.BaseEntity;
import br.com.mymoney.cadastrationservice.services.CrudService;
import br.com.mymoney.cadastrationservice.utils.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static br.com.mymoney.cadastrationservice.filters.JWTAuthenticationFilter.BEARER;
import static br.com.mymoney.cadastrationservice.filters.JWTAuthenticationFilter.EMPYT;

@Log
public abstract class CrudController<T extends BaseEntity<ID>, ID> {

    @Autowired protected CrudService<T, ID> crudService;
    @Autowired protected MessageSource messageSource;
    @Value("${security.jwt.secret:}")
    protected String secret;

    @PostMapping
    public ResponseEntity<T> create(@Valid @RequestBody T entity, BindingResult bindingResult) throws ResponseErrorException {
        if(bindingResult.hasErrors()){
             Set<ResponseErrorDto> errors = bindingResult.getFieldErrors().stream()
                    .map(ResponseErrorDto::new)
                     .collect(Collectors.toSet());

             throw new ResponseErrorException(errors);
        }

        return crudService.create(Optional.of(entity))
                .map(record -> ResponseEntity.ok(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> fullUpdate(@RequestBody T entity, @PathVariable("id") ID id){
        return crudService.fullUpdate(Optional.ofNullable(entity), Optional.of(id))
                .map(record -> ResponseEntity.ok(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<T> partialUpdate(HttpServletRequest request,
                                    @PathVariable("id") ID id){
        try{
            return crudService.partialUpdate(request.getInputStream(), Optional.of(id))
                    .map(record -> ResponseEntity.ok(record))
                    .orElse(ResponseEntity.badRequest().build());
        }catch (IOException ex){
            log.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ResponseErrorException(Set.of(new ResponseErrorDto(messageSource.getMessage("error.deserialization.json", null, null))));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") ID id) {
        boolean deleted = crudService.delete(Optional.of(id));
        if(deleted)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable("id") ID id) {
        return crudService.findById(Optional.of(id))
                .map(record -> ResponseEntity.ok(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping
    public ResponseEntity<ResponsePageDto<T>> findAll(HttpServletRequest request,
                                                      @RequestParam(name = "page", defaultValue = "0") int page,
                                                      @RequestParam(name = "size", defaultValue = "30") int size,
                                                      @RequestParam(name = "order", required = false) String[] order,
                                                      @RequestParam(name = "direction", defaultValue = "ASC") String direction) {
        return crudService.findAll(createDefaultSpecification(request), page, size, order, direction)
                .map(record -> ResponseEntity.ok(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Deve ser implementado quando se deseja ter um specification na consulta padrão do controller
     * @param request A requisição atual feito pelo usuário.
     * @return Um Specification do tipo da entidade com as regras desejada.
     */
    protected Specification<T> createDefaultSpecification(HttpServletRequest request){
        return null;
    }

    protected Optional<UUID> getUUIDAuthenticated(HttpServletRequest request){
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorization != null && !authorization.isBlank() && authorization.startsWith(BEARER)){
            return JWTUtil.getSubject(authorization.replace(BEARER, EMPYT), secret)
                    .map(p -> Optional.of(UUID.fromString(p)))
                    .orElse(Optional.empty());
        } else return Optional.empty();
    }
}
