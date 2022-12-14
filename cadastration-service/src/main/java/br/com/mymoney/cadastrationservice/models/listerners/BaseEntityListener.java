package br.com.mymoney.cadastrationservice.models.listerners;

import br.com.mymoney.cadastrationservice.models.entities.BaseEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class BaseEntityListener {

    @PrePersist
    @PreUpdate
    public void preSave(BaseEntity baseEntity){
        if(baseEntity.getUuid() == null) baseEntity.setUuid(UUID.randomUUID());
        baseEntity.setLastUpdate(LocalDateTime.now(ZoneOffset.UTC));
    }
}
