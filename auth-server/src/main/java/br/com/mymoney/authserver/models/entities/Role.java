package br.com.mymoney.authserver.models.entities;

import br.com.mymoney.crudcommon.models.entities.BaseEntity;
import br.com.mymoney.crudcommon.models.listerners.BaseEntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "role")
@EntityListeners(BaseEntityListener.class)
public class Role extends BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
    @SequenceGenerator(name = "role_sequence", sequenceName = "role_sequence_seq_id")
    private Integer id;

    @NotBlank(message = "{validation.model.Role.name.NotBlank}")
    @Column(length = 100, nullable = false)
    private String name;

    private boolean actived;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission",
        joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_role_permission__role")),
        inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_role_permission__permission")))
    private Set<Permission> permissions;

    @Column(nullable = false, updatable = false)
    private UUID uuid;

    private LocalDateTime lastUpdate;
}
