package pl.kedziorek.medicalcentreapplication.domain.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequest {
    private UUID userUuid;
    private UUID researchProjectUuid;
}
