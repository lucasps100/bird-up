package shepherd.birdup.domain;

import org.springframework.stereotype.Service;
import shepherd.birdup.data.CommentRepository;
import shepherd.birdup.models.Comment;
import shepherd.birdup.models.Like;

import javax.validation.Validator;
import java.util.List;

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

//    public Result<Comment> create(Comment comment) {
//        Result<Comment> result;
//        if (comment == null) {
//            result = new Result<>();
//            result.addMessage(ResultType.INVALID, "nothing to add");
//            return result;
//        }
//        result = valid
//    }


}
