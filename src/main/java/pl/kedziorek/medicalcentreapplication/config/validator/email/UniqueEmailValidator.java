package pl.kedziorek.medicalcentreapplication.config.validator.email;

import org.springframework.beans.factory.annotation.Autowired;
import pl.kedziorek.medicalcentreapplication.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueEmail unique) {
        unique.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        try{
            boolean exists = userRepository.existsUserByEmail(email);
            return !exists;
        } catch (NullPointerException nullPointerException){
            return true;
        }
    }
}
