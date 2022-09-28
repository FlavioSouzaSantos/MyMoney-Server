package br.com.mymoney.cadastrationservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor

@Entity
@Table(name = "credit_card_type")
public class CreditCardType extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_card_type_sequence")
    @SequenceGenerator(name = "credit_card_type_sequence", sequenceName = "credit_card_type_sequence_seq_id")
    private Long id;

    @NotBlank(message = "{validation.model.CreditCardType.name.NotBlank}")
    @Column(nullable = false, length = 100)
    private String name;

    private boolean active = true;
}
