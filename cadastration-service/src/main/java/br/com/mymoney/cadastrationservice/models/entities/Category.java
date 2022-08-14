package br.com.mymoney.cadastrationservice.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    @SequenceGenerator(name = "category_sequence", sequenceName = "category_sequence_seq_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parent", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_category__parent"))
    private Category parent;

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
