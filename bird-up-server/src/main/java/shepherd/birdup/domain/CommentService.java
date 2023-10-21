package shepherd.birdup.domain;

import org.springframework.stereotype.Service;
import shepherd.birdup.data.CommentRepository;
import shepherd.birdup.models.Comment;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class CommentService {
    private final CommentRepository repository;

    private final Validator validator;

    public CommentService(CommentRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Comment findById(int commentId) {
        return repository.findById(commentId);
    }

    public List<Comment> findByPostId(int postId) {
        return repository.findByPostId(postId);
    }

    public Result<Comment> create(Comment comment) {
        Result<Comment> result = new Result<>();
        if (comment == null) {
            result.addMessage(ResultType.INVALID, "nothing to add");
            return result;
        }
        if (comment.getCommentId() > 0) {
            result.addMessage(ResultType.INVALID, "comment id should not be set");
            return result;
        }
        result = validateComment(comment);
        if (result.isSuccess()) {
            comment = repository.create(comment);
            result.setPayload(comment);
        }
        return result;
    }

    Result<Comment> update(Comment comment) {
        Result<Comment> result = new Result<>();
        if (comment == null) {
            result.addMessage(ResultType.INVALID, "nothing to update");
            return result;
        }
        result = validateComment(comment);
        if (!result.isSuccess()) {
            return result;
        }

        if (comment.getCommentId() < 1) {
            result.addMessage(ResultType.INVALID, "comment id is required");
            return result;
        }
        if (!repository.update(comment)) {
            result.addMessage(ResultType.NOT_FOUND, String.format("comment %s not found", comment.getCommentId()));
        }
        return result;
    }

    public Result<Comment> deleteByCommentId(int commentId) {
        Result<Comment> result = new Result<>();
        if (!repository.deleteByCommentId(commentId)) {
            String msg = String.format("comment %s not found", commentId);
            result.addMessage(ResultType.NOT_FOUND, msg);
        }
        return result;
    }


    private Result<Comment> validateComment(Comment comment) {
        Result<Comment> result = new Result<>();

        Set<ConstraintViolation<Object>> violations = validator.validate(comment);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<?> violation : violations) {
                result.addMessage(ResultType.INVALID, violation.getMessage());
            }
        }
        return result;
    }


}
