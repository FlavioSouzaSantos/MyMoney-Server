package br.com.mymoney.cadastrationservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_sequence")
    @SequenceGenerator(name = "tag_sequence", sequenceName = "tag_sequence_seq_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

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
