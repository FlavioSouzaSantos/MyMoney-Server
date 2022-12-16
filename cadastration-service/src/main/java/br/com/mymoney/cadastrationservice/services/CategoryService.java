package br.com.mymoney.cadastrationservice.services;

import br.com.mymoney.cadastrationservice.models.entities.Category;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService extends CrudService<Category, Long> {
    @Override
    protected void setDefaultValues(Optional<Category> entity, HttpServletRequest request) {
        if(entity.isPresent() && entity.get().getId() == null){
            getUUIDAuthenticated(request).ifPresent(p -> entity.get().setUserId(p));
        }
    }
}
