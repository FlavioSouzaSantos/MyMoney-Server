package br.com.mymoney.cadastrationservice.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Getter
@Setter
public abstract class BaseEntity<ID> {

    @Column(nullable = false, updatable = false)
    private UUID uuid;

    private LocalDateTime lastUpdate;

    @PrePersist
    @PreUpdate
    public void preSave(){
        if(uuid == null) uuid = UUID.randomUUID();
        if(lastUpdate == null) lastUpdate = LocalDateTime.now(ZoneOffset.UTC);
    }

    public abstract void setId(ID id);
}
