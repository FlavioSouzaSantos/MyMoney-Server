package br.com.mymoney.crudcommon.models.listerners;

import br.com.mymoney.crudcommon.models.entities.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

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
