package pl.kedziorek.medicalcentreapplication.domain.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;

    @Builder.Default
    private String type = "Bearer";

    private String username;

    private String uuid;

    private List<String> roles;
}
