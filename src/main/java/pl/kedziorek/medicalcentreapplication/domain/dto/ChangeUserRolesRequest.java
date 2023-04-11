package pl.kedziorek.medicalcentreapplication.domain.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserRolesRequest {
    private Set<String> roles = new HashSet<>();
}
