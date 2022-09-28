package br.com.mymoney.cadastrationservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "category")
public class Category extends BaseEntity<Long> {

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

    private boolean syncRelease;
}
