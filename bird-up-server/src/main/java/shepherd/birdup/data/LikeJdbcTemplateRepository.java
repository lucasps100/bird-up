package shepherd.birdup.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shepherd.birdup.data.mappers.LikeMapper;
import shepherd.birdup.models.Like;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class LikeJdbcTemplateRepository {
    private final JdbcTemplate jdbcTemplate;

    public LikeJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Like> findByPostId(int postId) {
        final String sql = """
                select a.app_user_id, username, first_name, last_name, bio, created_at
                from like join app_user a
                on like.user_liker_id = a.app_user_id
                join profile
                on a.app_user_id = profile.app_user_id
                join post po
                on post.post_id = like.post_id
                where post_id = ? AND a.enabled = true AND po.enabled = true;
                """;

        return jdbcTemplate.query(sql, new LikeMapper(), postId);
    }

    public Like add(Like like) {
        final String sql = """
                insert into like (user_liker_id, post_id) value(?, ?);
                """;
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, like.getLikerAccount().getAppUserId());
            ps.setInt(2, like.getPostId());
            return ps;
        });

        if (rowsAffected <= 0) {
            return null;
        }
        return like;
    }

    public boolean deleteByIds(int appUserId, int postId) {
        return jdbcTemplate.update("delete from like where user_liker_id = ? AND post_id = ?;",
                appUserId,
                postId) > 0;
    }
}
