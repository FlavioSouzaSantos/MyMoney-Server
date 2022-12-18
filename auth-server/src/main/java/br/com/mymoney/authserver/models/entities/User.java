package br.com.mymoney.authserver.models.entities;

import br.com.mymoney.crudcommon.models.entities.BaseEntity;
import br.com.mymoney.crudcommon.models.listerners.BaseEntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "user")
@EntityListeners(BaseEntityListener.class)
public class User extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence_seq_id")
    private Long id;

    @NotBlank(message = "{validation.model.User.name.NotBlank}")
    @Column(length = 100, nullable = false)
    private String name;

    @NotBlank(message = "{validation.model.User.email.NotBlank}")
    @Email(message = "{validation.model.User.email.Invalid}")
    @Column(length = 100, nullable = false)
    private String email;

    @NotBlank(message = "{validation.model.User.phoneNumber.NotBlank}")
    @Column(length = 14, nullable = false)
    private String phoneNumber;

    private boolean actived;

    private boolean blocked;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_role__user")),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_user_role__role")))
    private Set<Permission> roles;

    @Column(nullable = false, updatable = false)
    private UUID uuid;

    private LocalDateTime lastUpdate;
}
