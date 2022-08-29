package br.com.mymoney.cadastrationservice.services;

import br.com.mymoney.cadastrationservice.models.entities.Category;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class CategoryService extends CrudService<Category, Long> {
    @Override
    protected void updateFields(Optional<HashMap<String, Object>> from, Optional<Category> to) {

    }
}
