package br.com.mymoney.cadastrationservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor

@Entity
@Table(name = "account_type")
public class AccountType extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_type_sequence")
    @SequenceGenerator(name = "account_type_sequence", sequenceName = "account_type_sequence_seq_id")
    private Long id;

    @NotBlank(message = "{validation.model.AccountType.name.NotBlank}")
    @Column(length = 100, nullable = false)
    private String name;

    private boolean active = true;
}
