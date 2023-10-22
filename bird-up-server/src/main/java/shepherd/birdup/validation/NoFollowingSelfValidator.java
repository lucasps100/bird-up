package shepherd.birdup.validation;

import shepherd.birdup.models.Follower;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoFollowingSelfValidator implements ConstraintValidator<NoFollowingSelf, Follower> {

    @Override
    public boolean isValid(Follower follower, ConstraintValidatorContext constraintValidatorContext) {
        if (follower == null) {
            return false;
        }
        if (follower.getFollower() == null || follower.getFollowee() == null) {
            return false;
        }
        return follower.getFollower().getAppUserId() != follower.getFollowee().getAppUserId();
    }
}
