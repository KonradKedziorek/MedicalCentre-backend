package pl.kedziorek.medicalcentreapplication.domain;

import lombok.*;
import org.apache.commons.text.RandomStringGenerator;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.kedziorek.medicalcentreapplication.config.validator.email.UniqueEmail;
import pl.kedziorek.medicalcentreapplication.config.validator.pesel.UniquePesel;
import pl.kedziorek.medicalcentreapplication.config.validator.phoneNumber.ValidPhoneNumber;
import pl.kedziorek.medicalcentreapplication.domain.dto.DoctorsDto;
import pl.kedziorek.medicalcentreapplication.domain.dto.UserRequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "user", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Surname is mandatory")
    private String surname;

    @NotBlank(message = "Email is mandatory")
    @UniqueEmail(message = "User with this email address already exist!")
    @Email
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Column(length = 256)
    private String password;

    @NotBlank(message = "Pesel is mandatory")
    @UniquePesel(message = "User with this pesel already exist!")
    private String pesel;

    @NotBlank(message = "Phone number is mandatory")
    @ValidPhoneNumber(message = "Invalid phone number!")
    private String phoneNumber;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private Boolean deleted = Boolean.FALSE;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static User map(UserRequest userRequest, Set<Role> roles) {
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder().withinRange(48, 57).withinRange(65,90).withinRange(97,122).build();
        return User.builder()
                .uuid(Objects.equals(userRequest.getUuid(), "") ? UUID.randomUUID() : UUID.fromString(userRequest.getUuid()))
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .email(userRequest.getEmail())
                .password(userRequest.getPesel() + randomStringGenerator.generate(5))
                .pesel(userRequest.getPesel())
                .phoneNumber(userRequest.getPhoneNumber())
                .createdBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .createdAt(LocalDateTime.now())
                .deleted(Boolean.FALSE)
                .roles(roles)
                .build();
    }

    public static User mapToDoctors(DoctorsDto doctorsDto) {
        return User.builder()
                .id(doctorsDto.getId())
                .name(doctorsDto.getSurname())
                .build();
    }
}
