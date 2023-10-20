package shepherd.birdup.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import shepherd.birdup.data.ProfileJdbcTemplateRepository;
import shepherd.birdup.data.ProfileRepository;
import shepherd.birdup.models.Profile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class ProfileExistsValidator implements ConstraintValidator<ProfileExists, Profile> {

    @Autowired
    private ProfileRepository repository;
    @Override
    public void initialize(ProfileExists constraintAnnotation) {}

    @Override
    public boolean isValid(Profile profile, ConstraintValidatorContext constraintValidatorContext) {
        if(profile == null) {
            return false;
        }
        Profile existing = repository.findById(profile.getAppUserId());
        return existing != null;
    }
}
