package shepherd.birdup.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {DistinctFollowValidator.class})
@Documented
public @interface DistinctFollow {
    String message() default "user already follows this profile";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
