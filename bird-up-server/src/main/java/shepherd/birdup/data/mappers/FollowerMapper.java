package shepherd.birdup.data.mappers;

import org.springframework.jdbc.core.RowMapper;
import shepherd.birdup.models.Follower;
import shepherd.birdup.models.Profile;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FollowerMapper implements RowMapper<Follower> {
    @Override
    public Follower mapRow(ResultSet rs, int rowNum) throws SQLException {
        Follower follower = new Follower();

        Profile ler = new Profile();
        ler.setAppUserId(rs.getInt("follower_id"));

        Profile lee = new Profile();
        lee.setAppUserId(rs.getInt("followee_id"));

        follower.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        follower.setFollower(ler);
        follower.setFollowee(lee);
        return follower;
    }
}
