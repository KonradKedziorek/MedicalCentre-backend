package pl.kedziorek.medicalcentreapplication.config.validator.roleName;

import org.springframework.beans.factory.annotation.Autowired;
import pl.kedziorek.medicalcentreapplication.repository.RoleRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueRoleNameValidator implements ConstraintValidator<UniqueRoleName,String> {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void initialize(UniqueRoleName unique) {
        unique.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        try{
            boolean exists = roleRepository.existsByName(name);
            return !exists;
        }catch (NullPointerException nullPointerException){
            return true;
        }
    }
}
