package shepherd.birdup.validation;

import org.springframework.beans.factory.annotation.Autowired;
import shepherd.birdup.data.StateRepository;
import shepherd.birdup.models.State;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StateExistsValidator implements ConstraintValidator<StateExists, State> {

    @Autowired
    StateRepository repository;

    @Override
    public void initialize(StateExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(State state, ConstraintValidatorContext constraintValidatorContext) {
        if (state == null) {
            return false;
        }
        return repository.findStateById(state.getStateId()) != null;
    }
}
