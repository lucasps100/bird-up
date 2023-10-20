package shepherd.birdup.data;

import org.springframework.jdbc.core.JdbcTemplate;
import shepherd.birdup.models.Follower;

import java.sql.PreparedStatement;

public class FollowerJdbcTemplateRepository implements FollowerRepository{

    private final JdbcTemplate jdbcTemplate;

    public FollowerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Follower findByIds(int followerId, int followeeId) {
        final String sql = """
                select * from follower
                where follower_id = ? and followee_id = ?;
                """;
        return null;
    }

    @Override
    public Follower create(Follower follower) {
        final String sql = """
                insert into follower(follower_id, followee_id)
                value(?, ?);
                """;

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, follower.getFollowerId());
            ps.setInt(2, follower.getFolloweeId());
            return ps;
        });

        if (rowsAffected <= 0) {
            return null;
        }
        return follower;
    }

    @Override
    public boolean deleteByIds(int followerId, int followeeId) {
        final String sql = """
                delete from follower
                where
                follower_id =?
                AND followee_id = ?;
                """;
        return jdbcTemplate.update(sql, followerId, followeeId) > 0;
    }
}
