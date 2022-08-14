package br.com.mymoney.cadastrationservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "credit_card_type")
public class CreditCardType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_card_type_sequence")
    @SequenceGenerator(name = "credit_card_type_sequence", sequenceName = "credit_card_type_sequence_seq_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    private boolean active;

    @Column(nullable = false, updatable = false)
    private UUID uuid;

    @PrePersist
    public void prePersiste(){
        if(uuid == null) uuid = UUID.randomUUID();
    }
}
