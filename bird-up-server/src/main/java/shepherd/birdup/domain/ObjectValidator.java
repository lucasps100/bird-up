package shepherd.birdup.domain;

import shepherd.birdup.models.Like;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class ObjectValidator {

    private final Validator validator;

    public ObjectValidator(Validator validator) {
        this.validator = validator;
    }

    public Result<?> validate(Object o) {
        Result<Like> result = new Result<>();

        Set<ConstraintViolation<Object>> violations = validator.validate(o);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<?> violation : violations) {
                result.addMessage(ResultType.INVALID, violation.getMessage());
            }
        }
        return result;
    }
}
