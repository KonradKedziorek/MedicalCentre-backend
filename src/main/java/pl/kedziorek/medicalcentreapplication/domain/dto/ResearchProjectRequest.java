package pl.kedziorek.medicalcentreapplication.domain.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResearchProjectRequest {
    private String uuid;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    private Set<DoctorsDto> doctors = new HashSet<>();
}
