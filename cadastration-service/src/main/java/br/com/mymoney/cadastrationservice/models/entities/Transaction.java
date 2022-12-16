package br.com.mymoney.cadastrationservice.models.entities;

import br.com.mymoney.cadastrationservice.models.listerners.BaseEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "transaction")
@EntityListeners(BaseEntityListener.class)
public class Transaction extends BaseEntity<Long> {
    public static final String USER_ID = "userId";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_sequence")
    @SequenceGenerator(name = "transaction_sequence", sequenceName = "transaction_sequence_seq_id")
    private Long id;

    @NotNull(message = "{validation.model.Transaction.type.NotNull}")
    @Enumerated
    private TransactionType type;

    @NotBlank(message = "{validation.model.Transaction.description.NotBlank}")
    @Length(max = 150, message = "{validation.model.Transaction.description.Length}")
    @Column(length = 150, nullable = false)
    private String description;

    @NotNull(message = "{validation.model.Transaction.date.NotNull}")
    private LocalDate data;

    @DecimalMin(message = "{validation.model.Transaction.value.DecimalMin}", value = "0.1")
    @Column(precision = 2, nullable = false)
    private double value;

    @NotNull(message = "{validation.model.Transaction.category.NotNull}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_transaction__category"))
    private Category category;

    @NotNull(message = "{validation.model.Transaction.account.NotNull}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account", referencedColumnName = "id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_transaction__account"))
    private Account account;

    private boolean fixed;

    @Length(max = 250, message = "{validation.model.Transaction.observation.Length}")
    @Column(length = 250)
    private String observation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tag", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_transaction__tag"))
    private Tag tag;

    @Min(value = 1, message = "{validation.model.Transaction.installmentNumber.Min}")
    private int installmentNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_credit_card", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_transaction__credit_card"))
    private CreditCard creditCard;

    @Column(nullable = false, updatable = false)
    private UUID userId;

    @Column(nullable = false, updatable = false)
    private UUID uuid;

    @Column(updatable = false)
    private UUID uuidGroup;

    @Column(nullable = false)
    private LocalDateTime lastUpdate;

    @JsonIgnore
    private boolean syncRelease;
}
