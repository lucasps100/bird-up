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
@Constraint(validatedBy = {DistinctLikeValidator.class})
@Documented
public @interface DistinctLike {
    String message() default "user already liked this post";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
