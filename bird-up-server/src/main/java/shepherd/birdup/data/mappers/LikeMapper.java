package shepherd.birdup.data.mappers;

import org.springframework.jdbc.core.RowMapper;
import shepherd.birdup.models.Like;
import shepherd.birdup.models.Profile;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeMapper implements RowMapper<Like> {

    @Override
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
        Like like = new Like();
        like.setPostId(rs.getInt("post_id"));

        Profile profile= new Profile();
        profile.setAppUserId(rs.getInt("app_user_id"));
        profile.setUsername(rs.getString("username"));
        profile.setFirstName(rs.getString("first_name"));
        profile.setLastName(rs.getString("last_name"));
        profile.setBio(rs.getString("bio"));
        like.setLikerAccount(profile);

        return like;
    }
}
