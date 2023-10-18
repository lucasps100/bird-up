package shepherd.birdup.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import shepherd.birdup.data.mappers.CommentMapper;
import shepherd.birdup.models.Comment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class CommentJdbcTemplateRepository implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Comment> findByPostId(int postId) {
        final String sql = """
                select a.app_user_id, username, first_name, last_name, bio, comment_id, comment_text, post_id, created_at, a.enabled, po.enabled
                from comment
                join app_user a
                on comment.user_commenter_id = a.app_user_id
                join profile
                on a.app_user_id = profile.app_user_id
                join post po
                on po.post_id = comment.post_id
                where post_id = ? AND a.enabled = true AND po.enabled=true
                order by comment.created_at;
                """;
        return jdbcTemplate.query(sql, new CommentMapper(), postId);
    }

    @Override
    public Comment create(Comment comment) {
        final String sql = """
        insert into comment (comment_text, user_commenter_id, post_id)
        value (?, ?, ?);
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, comment.getCommentText());
            ps.setInt(2, comment.getCommenterProfile().getAppUserId());
            ps.setInt(3, comment.getPostId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        comment.setCommentId(keyHolder.getKey().intValue());

        return comment;
    }

    @Override
    public boolean update(Comment comment) {
        final String sql = """
                update comment set
                comment_text = ?,
                user_commenter_id = ?,
                post_id = ?,
                where comment_id = ?;
                """;
        return jdbcTemplate.update(sql, comment.getCommentText(), comment.getCommenterProfile().getAppUserId(),
                comment.getPostId(), comment.getCommentId()) > 0;
    }

    @Override
    public boolean deleteByCommentId(int commentId) {
        final String sql = """
                delete from comment where comment_id = ?;
                """;
        return jdbcTemplate.update(sql, commentId) > 0;
    }
}
