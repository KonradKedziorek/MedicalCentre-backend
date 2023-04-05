package pl.kedziorek.medicalcentreapplication.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kedziorek.medicalcentreapplication.config.exception.ResourceNotFoundException;
import pl.kedziorek.medicalcentreapplication.config.security.AuthenticationProviderService;
import pl.kedziorek.medicalcentreapplication.config.security.JwtUtils;
import pl.kedziorek.medicalcentreapplication.domain.User;
import pl.kedziorek.medicalcentreapplication.domain.dto.AuthResponse;
import pl.kedziorek.medicalcentreapplication.domain.dto.Credentials;
import pl.kedziorek.medicalcentreapplication.repository.UserRepository;
import pl.kedziorek.medicalcentreapplication.service.AuthService;
import pl.kedziorek.medicalcentreapplication.service.UserService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final UserRepository userRepository;

    private final AuthenticationProviderService authenticationProviderService;

    private final JwtUtils jwtUtils;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AuthResponse authenticate(Credentials credentials) throws IllegalAccessException {
        User user = userService.getUser(credentials.getEmail());
        String password = credentials.getPassword();
        if(user!=null && (bCryptPasswordEncoder.matches(password,
                user.getPassword()))){
            Authentication authentication = databaseAuthenticate(credentials);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            String refreshToken = jwtUtils.generateRefreshToken(authentication);
            UUID uuid = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found in the database")).getUuid();
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            return AuthResponse.builder()
                    .token(jwt)
                    .username(authentication.getName())
                    .refreshToken(refreshToken)
                    .uuid(uuid.toString())
                    .roles(roles)
                    .build();
        }
        throw new IllegalAccessException("");
    }

    private Authentication databaseAuthenticate(Credentials credentials) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
        return authenticationProviderService.authenticate(usernamePasswordAuthenticationToken);
    }
}

