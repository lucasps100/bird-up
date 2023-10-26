package shepherd.birdup.validation;

import org.springframework.beans.factory.annotation.Autowired;
import shepherd.birdup.data.AppUserRepository;
import shepherd.birdup.models.AppUser;
import shepherd.birdup.models.Profile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AppUserExistsValidator implements ConstraintValidator<AppUserExists, Profile> {

    @Autowired
    AppUserRepository repository;

    @Override
    public boolean isValid(Profile profile, ConstraintValidatorContext constraintValidatorContext) {
        if (profile == null) {
            return false;
        }
        AppUser existing = repository.findById(profile.getAppUserId());
        return existing != null;
    }
}
