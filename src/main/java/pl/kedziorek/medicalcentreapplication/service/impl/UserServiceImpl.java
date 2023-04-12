package pl.kedziorek.medicalcentreapplication.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kedziorek.medicalcentreapplication.config.exception.BadRequestException;
import pl.kedziorek.medicalcentreapplication.config.exception.ResourceNotFoundException;
import pl.kedziorek.medicalcentreapplication.domain.Address;
import pl.kedziorek.medicalcentreapplication.domain.Role;
import pl.kedziorek.medicalcentreapplication.domain.User;
import pl.kedziorek.medicalcentreapplication.domain.dto.DoctorsDto;
import pl.kedziorek.medicalcentreapplication.domain.dto.UserRequest;
import pl.kedziorek.medicalcentreapplication.repository.UserRepository;
import pl.kedziorek.medicalcentreapplication.service.AddressService;
import pl.kedziorek.medicalcentreapplication.service.EmailService;
import pl.kedziorek.medicalcentreapplication.service.RoleService;
import pl.kedziorek.medicalcentreapplication.service.UserService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService<User> {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AddressService addressService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUser(String email) {
        log.info("Fetching user {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found in the database"));
    }

    @Override
    @Valid
    public User saveUser(UserRequest userRequest) {
        log.info("Saving new user to the database");
        Set<Role> roles = roleService.getRolesByNames(userRequest.getRoles());

        Address address = addressService.findFirstByCityAndPostcodeAndStreetAndLocalNumberAndHouseNumber(
                userRequest.getCity(),
                userRequest.getPostcode(),
                userRequest.getStreet(),
                userRequest.getLocalNumber(),
                userRequest.getHouseNumber()
        );

        User user = User.map(userRequest, roles);

        String notEncodedPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAddress(address);

        userRepository.save(user);

        emailService.sendMail(emailService.prepareInfoMailAboutCreatedAccount(
                userRequest.getEmail(),
                userRequest.getName(),
                notEncodedPassword,
                user.getCreatedBy()));
        return user;
    }

    @Override
    public User editUser(UserRequest userRequest) {
        User user = userRepository.findByUuid(UUID.fromString(userRequest.getUuid())).orElseThrow(() ->
                new ResourceNotFoundException("User not found in database!"));

        if (user.getDeleted() == Boolean.TRUE) {
            throw new ResourceNotFoundException("User not found in database!");
        }

        var userRef = changePropertiesValue(userRequest, user);
        return userRepository.save(userRef);
    }

    @Override
    public User activateUserAccount(String pesel) {
        User user = userRepository.findByPesel(pesel).orElseThrow(() ->
                new ResourceNotFoundException("User not found in database!"));

        user.setDeleted(Boolean.FALSE);

        return userRepository.save(user);
    }


    private User changePropertiesValue(UserRequest userRequest, User user) {
        Set<Role> roles = roleService.getRolesByNames(userRequest.getRoles());

        Address address = addressService.findFirstByCityAndPostcodeAndStreetAndLocalNumberAndHouseNumber(
                userRequest.getCity(),
                userRequest.getPostcode(),
                userRequest.getStreet(),
                userRequest.getLocalNumber(),
                userRequest.getHouseNumber()
        );

        if (userRepository.existsUserByEmailAndUuidIsNot(userRequest.getEmail(), user.getUuid())) {
            throw new BadRequestException("User with that email already exist!");
        } else {
            user.setEmail(userRequest.getEmail());
        }

        if (userRepository.existsUserByPhoneNumberAndUuidIsNot(userRequest.getPhoneNumber(), user.getUuid())) {
            throw new BadRequestException("User with that phone number already exist!");
        } else {
            user.setPhoneNumber(userRequest.getPhoneNumber());
        }

        if (userRepository.existsUserByPeselAndUuidIsNot(userRequest.getPesel(), user.getUuid())) {
            throw new BadRequestException("User with that pesel already exist!");
        } else {
            user.setPesel(userRequest.getPesel());
        }

        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setAddress(address);
        user.setRoles(roles);

        user.setModifiedAt(LocalDateTime.now());
        user.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return user;
    }

    @Override
    public User deleteUser(UUID uuid) {
        User user = userRepository.findByUuid(uuid).orElseThrow(() ->
                new ResourceNotFoundException("User not found in database!")
        );

        if (user.getDeleted() == Boolean.TRUE) {
            throw new ResourceNotFoundException("User not found in database!");
        }

        user.setDeleted(Boolean.TRUE);

        return user;
    }

    @Override
    public Set<User> getUsersByIdAndSurname(Set<DoctorsDto> doctors) {
        Set<User> doctorsList = new HashSet<>();
        for (DoctorsDto doctor : doctors) {
            if (userRepository.findUserByIdAndSurname(doctor.getId(), doctor.getSurname()).isPresent()) {
                doctorsList.add(userRepository.findUserByIdAndSurname(doctor.getId(), doctor.getSurname())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                String.format("Doctor %s not found in the database", doctor.getSurname()))));
            }
        }
        return doctorsList;
    }
}
