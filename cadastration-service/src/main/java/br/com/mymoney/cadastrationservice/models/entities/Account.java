package br.com.mymoney.cadastrationservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence")
    @SequenceGenerator(name = "account_sequence", sequenceName = "account_sequence_seq_id")
    private Long id;

    @NotBlank(message = "{validation.model.Account.name.NotBlank}")
    @Column(length = 200, nullable = false)
    private String name;

    @NotNull(message = "{validation.model.Account.type.NotNull}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_account__account_type"))
    private AccountType type;

    @Column(precision = 2)
    private Double initialValue;

    private boolean deleted;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false, updatable = false)
    private UUID uuid;

    private LocalDateTime lastUpdate;

    private boolean syncRelease;

    @PrePersist
    @PreUpdate
    public void preSave(){
        if(uuid == null) uuid = UUID.randomUUID();
        if(lastUpdate == null) lastUpdate = LocalDateTime.now(ZoneOffset.UTC);
    }
}
