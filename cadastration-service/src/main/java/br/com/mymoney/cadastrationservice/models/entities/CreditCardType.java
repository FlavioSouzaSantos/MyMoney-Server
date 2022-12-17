package br.com.mymoney.cadastrationservice.models.entities;

import br.com.mymoney.crudcommon.models.entities.BaseEntity;
import br.com.mymoney.crudcommon.models.listerners.BaseEntityListener;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "credit_card_type")
@EntityListeners(BaseEntityListener.class)
public class CreditCardType extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_card_type_sequence")
    @SequenceGenerator(name = "credit_card_type_sequence", sequenceName = "credit_card_type_sequence_seq_id")
    private Long id;

    @NotBlank(message = "{validation.model.CreditCardType.name.NotBlank}")
    @Column(nullable = false, length = 100)
    private String name;

    private boolean active = true;

    @Column(nullable = false, updatable = false)
    private UUID uuid;

    private LocalDateTime lastUpdate;
}
