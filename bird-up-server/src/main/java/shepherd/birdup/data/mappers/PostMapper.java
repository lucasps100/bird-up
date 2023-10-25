package shepherd.birdup.data.mappers;

import org.springframework.jdbc.core.RowMapper;
import shepherd.birdup.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostMapper implements RowMapper<Post> {

    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
        post.setPostId(rs.getInt("post_id"));
        post.setPostText(rs.getString("post_body"));
        post.setImage(rs.getBlob("image"));
        post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        Species species = new Species();
        species.setSpeciesId(rs.getInt("species_id"));
        species.setSpeciesShortName(rs.getString("species_short_name"));
        species.setSpeciesLongName(rs.getString("species_long_name"));
        post.setSpecies(species);

        Profile profile = new Profile();
        profile.setAppUserId(rs.getInt("app_user_id"));
        profile.setUsername(rs.getString("username"));
        profile.setFirstName(rs.getString("first_name"));
        profile.setLastName(rs.getString("last_name"));
        profile.setBio(rs.getString("bio"));
        post.setPosterProfile(profile);

//        State state = new State();
//        state.setStateId(rs.getInt("state_id"));
//        state.setStateAbbrv(rs.getString("state_abbrv"));
//        state.setStateName(rs.getString("state_name"));
//
//        Location location = new Location();
//        location.setState(state);
//        location.setLocationId(rs.getInt("location_id"));
//        location.setCity(rs.getString("city"));
//        location.setPostalCode(rs.getString("postal_code"));
//        post.setPostLocation(location);

        return post;
    }
}
