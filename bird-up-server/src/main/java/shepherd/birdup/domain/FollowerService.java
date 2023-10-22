package shepherd.birdup.domain;

import org.springframework.stereotype.Service;
import shepherd.birdup.data.FollowerRepository;
import shepherd.birdup.models.Follower;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class FollowerService {
    private final FollowerRepository repository;

    private final Validator validator;

    public FollowerService(FollowerRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Follower findByIds(int followerId, int followeeId) {
        return repository.findByIds(followerId, followeeId);
    }

    public Result<Follower> create(Follower follower) {
        Result<Follower> result;
        if (follower == null) {
            result = new Result<>();
            result.addMessage(ResultType.INVALID, "nothing to add");
            return result;
        }
        result = validateFollower(follower);
        if (result.isSuccess()) {
            follower = repository.create(follower);
            result.setPayload(follower);
        }
        return result;
    }

    public Result<Follower> deleteByIds(int followerId, int followeeId) {
        Result<Follower> result = new Result<>();
        if (!repository.deleteByIds(followerId, followeeId)) {
            String msg = String.format("user %s does not follow user %s", followerId, followeeId);
            result.addMessage(ResultType.NOT_FOUND, msg);
        }
        return result;
    }

    private Result<Follower> validateFollower(Follower follower) {
        Result<Follower> result = new Result<>();

        Set<ConstraintViolation<Follower>> violations = validator.validate(follower);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<?> violation : violations) {
                result.addMessage(ResultType.INVALID, violation.getMessage());
            }
        }
        return result;
    }
}
