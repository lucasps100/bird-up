package shepherd.birdup.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
@Target({ANNOTATION_TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {PostalCodeValidator.class})
@Documented
public @interface PostalCode {
    String message() default "{invalid postal code}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
