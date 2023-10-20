package shepherd.birdup.validation;

import org.springframework.beans.factory.annotation.Autowired;
import shepherd.birdup.data.FollowerRepository;
import shepherd.birdup.models.Follower;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DistinctFollowValidator implements ConstraintValidator<DistinctFollow, Follower> {

    @Autowired
    FollowerRepository repository;

    @Override
    public void initialize(DistinctFollow constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Follower follower, ConstraintValidatorContext constraintValidatorContext) {
        if (follower == null) {
            return false;
        }
        return repository.findByIds(follower.getFollower().getAppUserId(), follower.getFollowee().getAppUserId()) == null;
    }
}
