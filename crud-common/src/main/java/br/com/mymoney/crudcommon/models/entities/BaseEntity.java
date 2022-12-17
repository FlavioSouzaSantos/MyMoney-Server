package br.com.mymoney.crudcommon.models.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public abstract class BaseEntity<ID> {
    public abstract void setId(ID id);
    public abstract void setUuid(UUID uuid);
    public abstract UUID getUuid();
    public abstract void setLastUpdate(LocalDateTime localDateTime);
    public abstract LocalDateTime getLastUpdate();
}
