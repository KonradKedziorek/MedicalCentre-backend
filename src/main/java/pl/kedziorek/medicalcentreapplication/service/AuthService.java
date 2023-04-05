package pl.kedziorek.medicalcentreapplication.service;

import pl.kedziorek.medicalcentreapplication.domain.dto.AuthResponse;
import pl.kedziorek.medicalcentreapplication.domain.dto.Credentials;

public interface AuthService {
    AuthResponse authenticate(Credentials credentials) throws IllegalAccessException;

}
