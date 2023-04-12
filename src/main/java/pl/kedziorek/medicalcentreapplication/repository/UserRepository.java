package pl.kedziorek.medicalcentreapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kedziorek.medicalcentreapplication.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUuid(UUID uuid);
    Optional<User> findByPesel(String pesel);
    Boolean existsUserByEmailAndUuidIsNot(String email, UUID uuid);
    Boolean existsUserByPeselAndUuidIsNot(String pesel, UUID uuid);
    Boolean existsUserByPhoneNumberAndUuidIsNot(String phoneNumber, UUID uuid);
    Boolean existsUserByEmail(String email);
    Boolean existsUserByPesel(String pesel);
    Optional<User> findUserByIdAndSurname(Long id, String surname);
}
