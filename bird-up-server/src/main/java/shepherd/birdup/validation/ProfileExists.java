package shepherd.birdup.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {ProfileExistsValidator.class})
@Documented
public @interface ProfileExists {
    String message() default "profile must exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
