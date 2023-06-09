package pl.kedziorek.medicalcentreapplication.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kedziorek.medicalcentreapplication.domain.User;
import pl.kedziorek.medicalcentreapplication.domain.dto.AuthResponse;
import pl.kedziorek.medicalcentreapplication.domain.dto.Credentials;
import pl.kedziorek.medicalcentreapplication.domain.dto.UserRequest;
import pl.kedziorek.medicalcentreapplication.service.AuthService;
import pl.kedziorek.medicalcentreapplication.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.UUID;

import static pl.kedziorek.medicalcentreapplication.utils.CookieUtils.buildCookie;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final AuthService authenticate;
    private final UserService userService;

    @Value("${domain}")
    private String domain;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Credentials credentials, HttpServletResponse response) throws IllegalAccessException {
        AuthResponse authResponse = authenticate.authenticate(credentials);
        Cookie tokenCookie = buildCookie(7 * 24 * 60 * 60, false, true, "/", domain, authResponse.getToken(), "token");
        response.addCookie(tokenCookie);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/user/save")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserRequest user) {
        return ResponseEntity.ok().body(userService.saveUser(user));
    }

    @GetMapping("/user/email={email}/get")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        return ResponseEntity.ok().body(userService.getUser(email));
    }

    @PutMapping("/user/edit")
    public ResponseEntity<User> editUser(
            @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok().body(userService.editUser(userRequest));
    }

    @PutMapping("/user/activateAccount/pesel={pesel}")
    public ResponseEntity<User> activateAccount(
            @PathVariable String pesel) {
        return ResponseEntity.ok().body(userService.activateUserAccount(pesel));
    }

    @PutMapping("/user/uuid={uuid}/delete")
    public ResponseEntity<User> deleteUser(
            @PathVariable UUID uuid) {
        return ResponseEntity.ok().body(userService.deleteUser(uuid));
    }
}