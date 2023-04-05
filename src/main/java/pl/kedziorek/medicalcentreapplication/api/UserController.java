package pl.kedziorek.medicalcentreapplication.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kedziorek.medicalcentreapplication.domain.dto.AuthResponse;
import pl.kedziorek.medicalcentreapplication.domain.dto.Credentials;
import pl.kedziorek.medicalcentreapplication.service.AuthService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static pl.kedziorek.medicalcentreapplication.utils.CookieUtils.buildCookie;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final AuthService authenticate;

    @Value("${domain}")
    private String domain;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credentials credentials, HttpServletResponse response) throws IllegalAccessException {
        AuthResponse authResponse = authenticate.authenticate(credentials);
        Cookie tokenCookie = buildCookie(7 * 24 * 60 * 60, true, true, "/", domain, authResponse.getToken(), "token");
        response.addCookie(tokenCookie);
        return ResponseEntity.ok(authResponse);
    }
}