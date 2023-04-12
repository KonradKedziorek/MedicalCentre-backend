package pl.kedziorek.medicalcentreapplication.service;

import pl.kedziorek.medicalcentreapplication.domain.User;
import pl.kedziorek.medicalcentreapplication.domain.dto.DoctorsDto;
import pl.kedziorek.medicalcentreapplication.domain.dto.UserRequest;

import java.util.Set;
import java.util.UUID;

public interface UserService<T> {
    User getUser(String username);
    User saveUser(UserRequest user);
    User editUser(UserRequest userRequest);
    User deleteUser(UUID uuid);
    Set<User> getUsersByIdAndSurname(Set<DoctorsDto> doctors);
    User activateUserAccount(String pesel);
}
