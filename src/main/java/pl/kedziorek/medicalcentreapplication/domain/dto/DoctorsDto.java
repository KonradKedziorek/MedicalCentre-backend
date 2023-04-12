package pl.kedziorek.medicalcentreapplication.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DoctorsDto {
    private Long id;
    private String surname;
}
