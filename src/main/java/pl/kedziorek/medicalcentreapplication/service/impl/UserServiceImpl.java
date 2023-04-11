package pl.kedziorek.medicalcentreapplication.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kedziorek.medicalcentreapplication.config.exception.ResourceNotFoundException;
import pl.kedziorek.medicalcentreapplication.domain.Address;
import pl.kedziorek.medicalcentreapplication.domain.Role;
import pl.kedziorek.medicalcentreapplication.domain.User;
import pl.kedziorek.medicalcentreapplication.domain.dto.UserRequest;
import pl.kedziorek.medicalcentreapplication.repository.UserRepository;
import pl.kedziorek.medicalcentreapplication.service.AddressService;
import pl.kedziorek.medicalcentreapplication.service.EmailService;
import pl.kedziorek.medicalcentreapplication.service.RoleService;
import pl.kedziorek.medicalcentreapplication.service.UserService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

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
}
