package shepherd.birdup.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {PostExistsValidator.class})
@Documented
public @interface PostExists {
    String message() default "post must exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
