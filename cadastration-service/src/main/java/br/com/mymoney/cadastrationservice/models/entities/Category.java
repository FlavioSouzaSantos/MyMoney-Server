package br.com.mymoney.cadastrationservice.models.entities;

import br.com.mymoney.cadastrationservice.models.listerners.BaseEntityListener;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "category")
@EntityListeners(BaseEntityListener.class)
public class Category extends BaseEntity<Long> {
    public static final String USER_ID = "userId";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    @SequenceGenerator(name = "category_sequence", sequenceName = "category_sequence_seq_id")
    private Long id;

    @NotBlank(message = "{validation.model.Category.name.NotBlank}")
    @Column(length = 100, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parent", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_category__parent"))
    private Category parent;

    private boolean active;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false, updatable = false)
    private UUID uuid;

    private LocalDateTime lastUpdate;

    private boolean syncRelease;
}
