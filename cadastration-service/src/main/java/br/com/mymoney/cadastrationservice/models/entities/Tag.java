package br.com.mymoney.cadastrationservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "tag")
public class Tag extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_sequence")
    @SequenceGenerator(name = "tag_sequence", sequenceName = "tag_sequence_seq_id")
    private Long id;

    @NotNull(message = "{validation.model.Tag.name.NotBlank}")
    @Column(nullable = false, length = 100)
    private String name;

    private boolean active = true;

    @Column(nullable = false)
    private UUID userId;

    private boolean syncRelease;
}
