package pl.kedziorek.medicalcentreapplication.domain;

import lombok.*;
import org.hibernate.Hibernate;
import pl.kedziorek.medicalcentreapplication.config.validator.roleName.UniqueRoleName;
import pl.kedziorek.medicalcentreapplication.domain.dto.RoleRequest;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "role", schema = "public")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Role name is mandatory")
    @UniqueRoleName(message = "This role already exist!")
    private String name;

    public static Role map(RoleRequest roleRequest) {
        return Role.builder()
                .name(roleRequest.getRoleName())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
