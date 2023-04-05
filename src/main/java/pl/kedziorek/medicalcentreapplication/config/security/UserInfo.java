package pl.kedziorek.medicalcentreapplication.config.security;

import lombok.*;
import pl.kedziorek.medicalcentreapplication.domain.Role;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private String username;

    private String password;

    private Set<Role> roles = new HashSet<>();
}

