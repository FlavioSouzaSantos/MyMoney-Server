package br.com.mymoney.cadastrationservice.services;

import br.com.mymoney.cadastrationservice.models.entities.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService extends CrudService<Tag, Long> {
    @Override
    protected void setDefaultValues(Optional<Tag> entity, HttpServletRequest request) {
        if(entity.isPresent() && entity.get().getId() == null){
            getUUIDAuthenticated(request).ifPresent(p -> entity.get().setUserId(p));
        }
    }
}
