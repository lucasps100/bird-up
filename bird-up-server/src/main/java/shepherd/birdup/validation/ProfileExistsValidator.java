package shepherd.birdup.validation;

import org.springframework.beans.factory.annotation.Autowired;
import shepherd.birdup.data.ProfileJdbcTemplateRepository;
import shepherd.birdup.models.Profile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class ProfileExistsValidator implements ConstraintValidator<ProfileExists, Profile> {

    @Autowired
    private ProfileJdbcTemplateRepository repository;
    @Override
    public void initialize(ProfileExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Profile profile, ConstraintValidatorContext constraintValidatorContext) {
        if(profile == null) {
            return false;
        }
        Profile existing = repository.findByUsername(profile.getUsername());
        return existing != null;
    }
}
