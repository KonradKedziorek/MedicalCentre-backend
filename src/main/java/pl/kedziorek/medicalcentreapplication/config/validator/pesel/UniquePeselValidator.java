package pl.kedziorek.medicalcentreapplication.config.validator.pesel;

import org.springframework.beans.factory.annotation.Autowired;
import pl.kedziorek.medicalcentreapplication.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniquePeselValidator implements ConstraintValidator<UniquePesel,String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniquePesel unique) {
        unique.message();
    }

    @Override
    public boolean isValid(String pesel, ConstraintValidatorContext context) {
        try{
            boolean exists = userRepository.existsUserByPesel(pesel);
            return !exists;
        } catch (NullPointerException nullPointerException){
            return true;
        }
    }
}
