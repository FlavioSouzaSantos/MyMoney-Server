package br.com.mymoney.cadastrationservice.services;

import br.com.mymoney.cadastrationservice.exceptions.ValidationException;
import br.com.mymoney.cadastrationservice.models.dtos.ResponsePageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class CrudService<T,ID> {

    protected final JpaRepository<T,ID> repository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Optional<T> create(Optional<T> entity) {
        if(entity.isPresent()){
            setDefaultValues(entity);
            return Optional.ofNullable(repository.save(entity.get()));
        }else return Optional.empty();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Optional<T> update(Optional<HashMap<String, Object>> entityFields, Optional<ID> id) {
        if(entityFields.isPresent() && id.isPresent() && repository.existsById(id.get())){
            Optional<T> to = repository.findById(id.get());
            updateFields(entityFields, to);
            return Optional.ofNullable(repository.save(to.get()));
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
    public Optional<ResponsePageDto<T>> findAll(int page, int size, String[] order, String direction) {
        PageRequest pageRequest;
        if(order != null && order.length > 0){
            pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), order);
        }else{
            pageRequest = PageRequest.of(page, size);
        }

        Page<T> pageResult = repository.findAll(pageRequest);
        return Optional.of(new ResponsePageDto(pageResult));
    }

    /**
     * Seta os valores padrão na entidade antes de ser salva.
     * @param entity Entidade a ser salva
     */
    protected void setDefaultValues(Optional<T> entity){}

    /**
     * Seta os fields que estão sendo alterados na entidade.
     * @param from Fields a ser alterados
     * @param to Entidade destino
     */
    protected abstract void updateFields(Optional<HashMap<String, Object>> from, Optional<T> to);

    /**
     * Valida se a entidade pode ser excluída.
     * @param entity
     * @throws ValidationException
     */
    protected void validateDeleteEntity(Optional<T> entity) throws ValidationException {}
}
