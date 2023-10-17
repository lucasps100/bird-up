package shepherd.birdup.data.mappers;

import org.springframework.jdbc.core.RowMapper;
import shepherd.birdup.models.Profile;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileMapper implements RowMapper<Profile> {


    @Override
    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
        Profile profile = new Profile();
        profile.setAppUserId(rs.getInt("app_user_id"));
        profile.setUsername(rs.getString("username"));
        profile.setFirstName(rs.getString("first_name"));
        profile.setLastName(rs.getString("last_name"));
        profile.setBio(rs.getString("bio"));
        profile.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return profile;
    }
}
