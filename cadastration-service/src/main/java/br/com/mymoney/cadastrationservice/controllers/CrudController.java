package br.com.mymoney.cadastrationservice.controllers;

import br.com.mymoney.cadastrationservice.exceptions.ResponseErrorException;
import br.com.mymoney.cadastrationservice.models.dtos.ResponseErrorDto;
import br.com.mymoney.cadastrationservice.models.dtos.ResponsePageDto;
import br.com.mymoney.cadastrationservice.services.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class CrudController<T,ID> {

    protected final CrudService<T,ID> crudService;

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
    public ResponseEntity<T> update(@RequestBody HashMap<String, Object> entityFields,
                                    @PathVariable("id") ID id){
        return crudService.update(Optional.ofNullable(entityFields), Optional.of(id))
                .map(record -> ResponseEntity.ok(record))
                .orElse(ResponseEntity.badRequest().build());
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
    public ResponseEntity<ResponsePageDto<T>> findAll(@RequestParam(name = "page", defaultValue = "1") int page,
                                                      @RequestParam(name = "size", defaultValue = "30") int size,
                                                      @RequestParam(name = "order") String[] order,
                                                      @RequestParam(name = "direction", defaultValue = "ASC") String direction) {
        return crudService.findAll(page, size, order, direction)
                .map(record -> ResponseEntity.ok(record))
                .orElse(ResponseEntity.badRequest().build());
    }
}
