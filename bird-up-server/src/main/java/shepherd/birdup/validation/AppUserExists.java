package shepherd.birdup.validation;

import shepherd.birdup.models.Profile;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {AppUserExistsValidator.class})
@Documented
public @interface AppUserExists{
    String message() default "app user not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
