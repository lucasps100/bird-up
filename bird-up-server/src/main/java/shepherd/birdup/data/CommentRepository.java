package shepherd.birdup.data;

import shepherd.birdup.models.Comment;

import java.util.List;

public interface CommentRepository {

    Comment findById(int commentId);

    List<Comment> findByPostId(int postId);

    Comment create(Comment comment);

    boolean update(Comment comment);

    boolean deleteByCommentId(int commentId);
}
