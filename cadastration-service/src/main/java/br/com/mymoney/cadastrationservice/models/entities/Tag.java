package br.com.mymoney.cadastrationservice.models.entities;

import br.com.mymoney.cadastrationservice.models.listerners.BaseEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "tag")
@EntityListeners(BaseEntityListener.class)
public class Tag extends BaseEntity<Long> {
    public static final String USER_ID = "userId";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_sequence")
    @SequenceGenerator(name = "tag_sequence", sequenceName = "tag_sequence_seq_id")
    private Long id;

    @NotBlank(message = "{validation.model.Tag.name.NotBlank}")
    @Column(nullable = false, length = 100)
    private String name;

    private boolean active = true;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false, updatable = false)
    private UUID uuid;

    private LocalDateTime lastUpdate;

    @JsonIgnore
    private boolean syncRelease;



}
