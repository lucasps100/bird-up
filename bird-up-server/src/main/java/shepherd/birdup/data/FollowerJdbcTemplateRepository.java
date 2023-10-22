package shepherd.birdup.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shepherd.birdup.models.Follower;

import java.sql.PreparedStatement;

@Repository
public class FollowerJdbcTemplateRepository implements FollowerRepository {

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
            ps.setInt(1, follower.getFollower().getAppUserId());
            ps.setInt(2, follower.getFollowee().getAppUserId());
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
