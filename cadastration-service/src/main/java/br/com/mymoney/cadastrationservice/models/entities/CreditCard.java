package br.com.mymoney.cadastrationservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "credit_card")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_card_sequence")
    @SequenceGenerator(name = "credit_card_sequence", sequenceName = "credit_card_sequence_seq_id")
    private Long id;

    @Column(length = 200, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_type", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_credit_card__credit_card_type"))
    private CreditCardType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_credit_card__account"))
    private Account account;

    private int closingDay;

    private int payingDay;

    @Column(nullable = false, precision = 2)
    private Double limit;

    private boolean deleted;

    private boolean active;

    private UUID userId;

    @Column(nullable = false, updatable = false)
    private UUID uuid;

    @PrePersist
    public void prePersiste(){
        if(uuid == null) uuid = UUID.randomUUID();
    }
}
