package shepherd.birdup.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shepherd.birdup.data.mappers.LikeMapper;
import shepherd.birdup.models.Like;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class LikeJdbcTemplateRepository implements LikeRepository {
    private final JdbcTemplate jdbcTemplate;

    public LikeJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Like> findByPostId(int postId) {
        final String sql = """
                select a.app_user_id, username, first_name, last_name, bio, po.post_id
                from post_like l
                join app_user a
                on a.app_user_id = l.user_liker_id
                join profile pr
                on pr.app_user_id = a.app_user_id
                join post po
                on po.post_id = l.post_id
                where po.post_id = ? AND a.enabled = true AND po.enabled = true;
                """;

        return jdbcTemplate.query(sql, new LikeMapper(), postId);
    }

    @Override
    public Like create(Like like) {
        final String sql = """
                insert into post_like (user_liker_id, post_id) value(?, ?);
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

    @Override
    public boolean deleteByIds(int appUserId, int postId) {
        return jdbcTemplate.update("delete from post_like where user_liker_id = ? AND post_id = ?;",
                appUserId,
                postId) > 0;
    }
}
