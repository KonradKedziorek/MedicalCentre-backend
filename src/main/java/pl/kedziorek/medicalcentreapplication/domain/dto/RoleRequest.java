package pl.kedziorek.medicalcentreapplication.domain.dto;

import lombok.*;
import pl.kedziorek.medicalcentreapplication.config.validator.roleName.UniqueRoleName;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {
    @UniqueRoleName(message = "Role with this name already exist!")
    String roleName;
}
