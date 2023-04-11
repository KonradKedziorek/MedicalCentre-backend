package pl.kedziorek.medicalcentreapplication.domain.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String uuid;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Surname is mandatory")
    private String surname;

    @NotBlank(message = "Email is mandatory")
//    @UniqueEmail(message = "User with this email address already exist!")
    @Email
    private String email;

    @NotBlank(message = "Pesel is mandatory")
//    @UniquePesel(message = "User with this pesel already exist!")
    private String pesel;

    @NotBlank(message = "Phone number is mandatory")
//    @ValidPhoneNumber(message = "Invalid phone number!")
//    @UniquePhoneNumber(message = "User with this phone number already exist!")
    private String phoneNumber;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "Postcode is mandatory")
    private String postcode;

    @NotBlank(message = "Street is mandatory")
    private String street;

    private String localNumber;

    @NotBlank(message = "House Number is mandatory")
    private String houseNumber;

    private Set<String> roles = new HashSet<>();
}
