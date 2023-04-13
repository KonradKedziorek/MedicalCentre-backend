package pl.kedziorek.medicalcentreapplication.domain.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultRequest {
    private String uuid;
    @NotBlank(message = "Description is mandatory")
    private String resultDescription;
    @NotBlank(message = "Commission is mandatory")
    private String commissionUuid;
}
