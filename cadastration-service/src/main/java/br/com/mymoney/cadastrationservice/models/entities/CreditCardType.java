package br.com.mymoney.cadastrationservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    @NotBlank(message = "{validation.model.CreditCardType.name.NotBlank}")
    @Column(nullable = false, length = 100)
    private String name;

    private boolean active = true;

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
