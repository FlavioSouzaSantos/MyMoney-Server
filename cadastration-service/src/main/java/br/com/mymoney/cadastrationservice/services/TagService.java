package br.com.mymoney.cadastrationservice.services;

import br.com.mymoney.cadastrationservice.models.entities.Tag;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class TagService extends CrudService<Tag, Long> {
    @Override
    protected void updateFields(Optional<HashMap<String, Object>> from, Optional<Tag> to) {

    }
}
