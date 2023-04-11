package pl.kedziorek.medicalcentreapplication.service;

import pl.kedziorek.medicalcentreapplication.domain.User;
import pl.kedziorek.medicalcentreapplication.domain.dto.UserRequest;

public interface UserService<T> {
    User getUser(String username);
    User saveUser(UserRequest user);
}
