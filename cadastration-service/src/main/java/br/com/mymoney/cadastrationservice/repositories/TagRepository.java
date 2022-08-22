package br.com.mymoney.cadastrationservice.repositories;

import br.com.mymoney.cadastrationservice.models.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
