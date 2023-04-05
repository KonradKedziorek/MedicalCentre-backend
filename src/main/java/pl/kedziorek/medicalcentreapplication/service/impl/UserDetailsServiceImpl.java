package pl.kedziorek.medicalcentreapplication.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kedziorek.medicalcentreapplication.config.security.UserDetailsImpl;
import pl.kedziorek.medicalcentreapplication.config.security.UserInfo;
import pl.kedziorek.medicalcentreapplication.domain.Role;
import pl.kedziorek.medicalcentreapplication.domain.User;
import pl.kedziorek.medicalcentreapplication.repository.UserRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email));
        UserInfo userInfo = UserInfo.builder()
                .username(user.getEmail())
                .roles((Set<Role>) user.getRoles())
                .build();
        return UserDetailsImpl.build(userInfo);
    }
}
