package shepherd.birdup.validation;

import org.springframework.beans.factory.annotation.Autowired;
import shepherd.birdup.data.PostRepository;
import shepherd.birdup.models.HasPostId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostExistsValidator implements ConstraintValidator<PostExists, HasPostId> {

    @Autowired
    private PostRepository repository;

    @Override
    public void initialize(PostExists constraintAnnotation) {}

    @Override
    public boolean isValid(HasPostId h, ConstraintValidatorContext constraintValidatorContext) {
        if(h == null) {
            return false;
        }
        return repository.findByPostId(h.getPostId()) != null;
    }
}
