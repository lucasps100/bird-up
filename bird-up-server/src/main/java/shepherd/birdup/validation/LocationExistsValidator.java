package shepherd.birdup.validation;

import org.springframework.beans.factory.annotation.Autowired;
import shepherd.birdup.data.LocationJdbcTemplateRepository;
import shepherd.birdup.models.Location;
import shepherd.birdup.models.Profile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LocationExistsValidator implements ConstraintValidator<LocationExists, Location>  {

    @Autowired
    LocationJdbcTemplateRepository repository;

    @Override
    public void initialize(LocationExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Location location, ConstraintValidatorContext constraintValidatorContext) {
        if(location == null) {
            return false;
        }
        return repository.findById(location.getLocationId()) != null;

    }
}
