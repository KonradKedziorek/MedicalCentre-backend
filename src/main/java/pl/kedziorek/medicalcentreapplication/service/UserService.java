package pl.kedziorek.medicalcentreapplication.service;

import pl.kedziorek.medicalcentreapplication.domain.User;
import pl.kedziorek.medicalcentreapplication.domain.dto.UserRequest;

import java.util.UUID;

public interface UserService<T> {
    User getUser(String username);
    User saveUser(UserRequest user);
    User editUser(UserRequest userRequest);
    User deleteUser(UUID uuid);
}
