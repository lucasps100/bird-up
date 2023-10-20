package shepherd.birdup.validation;

import org.springframework.beans.factory.annotation.Autowired;
import shepherd.birdup.data.SpeciesRepository;
import shepherd.birdup.models.Species;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SpeciesExistsValidator implements ConstraintValidator<SpeciesExists, Species> {

    @Autowired
    private SpeciesRepository repository;

    @Override
    public void initialize(SpeciesExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Species species, ConstraintValidatorContext constraintValidatorContext) {
        if (species == null) {
            return false;
        }
        Species existing = repository.findById(species.getSpeciesId());
        return existing != null;
    }
}
