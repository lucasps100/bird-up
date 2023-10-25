package shepherd.birdup.domain;

import org.springframework.stereotype.Service;
import shepherd.birdup.data.PostRepository;
import shepherd.birdup.models.Post;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class PostService {

    private final PostRepository repository;

    private final Validator validator;

    public PostService(PostRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public List<Post> findAll() {
        return repository.findAll();
    }

    public List<Post> findByAppUserId(int appUserId) {
        return repository.findByAppUserId(appUserId);
    }

//    public List<Post> findByStateAbbrv(String stateAbbrv) {
//        return repository.findByStateAbbrv(stateAbbrv);
//    }

    public List<Post> findBySpeciesShortName(String speciesShortName) {
        return repository.findBySpeciesShortName(speciesShortName);
    }

//    public List<Post> findByPostalCode(String postalCode) {
//        return repository.findByPostalCode(postalCode);
//    }

    public List<Post> findLikedPostsByLikerId(int likerId) {
        return repository.findLikedPostsByLikerId(likerId);
    }

    public List<Post> findFolloweePostsByFollowerId(int followerId) {
        return repository.findFolloweePostsByFollowerId(followerId);
    }

//    public List<Post> findByCityAndStateAbbrv(String city, String stateAbbrv) {
//        return repository.findByCityAndStateAbbrv(city, stateAbbrv);
//    }

    public Post findByPostId(int postId) {
        return repository.findByPostId(postId);
    }

    public Result<Post> create(Post post) {
        Result<Post> result = new Result<>();
        if (post == null) {
            result.addMessage(ResultType.INVALID, "nothing to add");
            return result;
        }
        if (post.getPostId() > 0) {
            result.addMessage(ResultType.INVALID, "post id should not be set");
            return result;
        }
        result = validatePost(post);
        if (result.isSuccess()) {
            post = repository.create(post);
            result.setPayload(post);
        }
        return result;
    }

    public Result<Post> update(Post post) {
        Result<Post> result = new Result<>();
        if (post == null) {
            result.addMessage(ResultType.INVALID, "nothing to update");
            return result;
        }
        result = validatePost(post);
        if (!result.isSuccess()) {
            return result;
        }

        if (post.getPostId() < 1) {
            result.addMessage(ResultType.INVALID, "post id is required");
            return result;
        }
        if (!repository.update(post)) {
            result.addMessage(ResultType.NOT_FOUND, String.format("post %s not found", post.getPostId()));
        }
        return result;
    }

    public Result<Post> softDeleteById(int postId) {
        Result<Post> result = new Result<>();
        if (!repository.softDeleteById(postId)) {
            String msg = String.format("post %s not found", postId);
            result.addMessage(ResultType.NOT_FOUND, msg);
        }
        return result;
    }

    private Result<Post> validatePost(Post post) {
        Result<Post> result = new Result<>();

        Set<ConstraintViolation<Object>> violations = validator.validate(post);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<?> violation : violations) {
                result.addMessage(ResultType.INVALID, violation.getMessage());
            }
        }
        return result;
    }
}
