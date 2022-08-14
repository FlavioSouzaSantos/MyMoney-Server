package br.com.mymoney.cadastrationservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Column(length = 200, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_account__account_type"))
    private AccountType type;

    @Column(precision = 2)
    private Double initialValue;

    private boolean deleted;

    private UUID userId;

    @Column(nullable = false, updatable = false)
    private UUID uuid;

    @PrePersist
    public void prePersiste(){
        if(uuid == null) uuid = UUID.randomUUID();
    }
}
