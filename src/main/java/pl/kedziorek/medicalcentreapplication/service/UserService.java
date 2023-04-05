package pl.kedziorek.medicalcentreapplication.service;

import pl.kedziorek.medicalcentreapplication.domain.User;

public interface UserService<T> {
    User getUser(String username);
}
