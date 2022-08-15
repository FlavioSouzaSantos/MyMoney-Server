package br.com.mymoney.cadastrationservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    @NotBlank(message = "{validation.model.CreditCard.name.NotBlank}")
    @Column(length = 200, nullable = false)
    private String name;

    @NotNull(message = "{validation.model.CreditCard.type.NotNull}")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_type", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_credit_card__credit_card_type"))
    private CreditCardType type;

    @NotNull(message = "{validation.model.CreditCard.account.NotNull}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_credit_card__account"))
    private Account account;

    @Min(value = 1, message = "{validation.model.CreditCard.closingDay.Min}")
    @Max(value = 31, message = "{validation.model.CreditCard.closingDay.Max}")
    private int closingDay;

    @Min(value = 1, message = "{validation.model.CreditCard.payingDay.Min}")
    @Max(value = 31, message = "{validation.model.CreditCard.payingDay.Max}")
    private int payingDay;

    @NotNull(message = "{validation.model.CreditCard.limit.NotNull}")
    @DecimalMin(value = "0.1", message = "{validation.model.CreditCard.limit.DecimalMin}")
    @Column(nullable = false, precision = 2)
    private Double limit;

    private boolean deleted;

    private boolean active=true;

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
