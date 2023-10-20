package shepherd.birdup.validation;

import org.springframework.beans.factory.annotation.Autowired;
import shepherd.birdup.data.PostJdbcTemplateRepository;
import shepherd.birdup.models.HasPostId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostExistsValidator implements ConstraintValidator<PostExists, HasPostId> {

    @Autowired
    PostJdbcTemplateRepository repository;
    @Override
    public void initialize(PostExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(HasPostId h, ConstraintValidatorContext constraintValidatorContext) {
        if(h == null) {
            return false;
        }
        return repository.findByPostId(h.getPostId()) != null;
    }
}
