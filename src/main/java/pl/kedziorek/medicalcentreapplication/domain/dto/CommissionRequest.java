package pl.kedziorek.medicalcentreapplication.domain.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommissionRequest {
    private String uuid;
    @NotBlank(message = "Type of research is mandatory!")
    private String typeOfResearch;
    private String description;
    private String userPesel;
}
