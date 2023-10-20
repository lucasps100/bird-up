package shepherd.birdup.domain;

import org.springframework.stereotype.Service;
import shepherd.birdup.data.LikeRepository;
import shepherd.birdup.models.Like;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class LikeService {

    private final LikeRepository repository;

    private final Validator validator;

    public LikeService(LikeRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public List<Like> findByPostId(int postId) {
        return repository.findByPostId(postId);
    }

    public Result<Like> create(Like like) {
        Result<Like> result;
        if (like == null) {
            result = new Result<>();
            result.addMessage(ResultType.INVALID, "nothing to add");
            return result;
        }
        result = validateLike(like);
        if (result.isSuccess()) {
            like = repository.create(like);
            result.setPayload(like);
        }
        return result;
    }

    public Result<Like> deletebyIds(int appUserId, int postId) {
        Result<Like> result = new Result<>();
        if (!repository.deleteByIds(appUserId, postId)) {
            String msg = String.format("User %s never liked post %s", appUserId, postId);
            result.addMessage(ResultType.NOT_FOUND, msg);
        }
        return result;
    }

    private Result<Like> validateLike(Like like) {
        Result<Like> result = new Result<>();

        Set<ConstraintViolation<Like>> violations = validator.validate(like);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<?> violation : violations) {
                result.addMessage(ResultType.INVALID, violation.getMessage());
            }
        }
        return result;
    }

}
