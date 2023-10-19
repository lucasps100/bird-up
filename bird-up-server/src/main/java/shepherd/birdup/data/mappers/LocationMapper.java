package shepherd.birdup.data.mappers;

import org.springframework.jdbc.core.RowMapper;
import shepherd.birdup.models.Location;
import shepherd.birdup.models.State;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationMapper implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet rs, int i) throws SQLException {
        Location location = new Location();
        location.setLocationId(rs.getInt("location_id"));
        location.setCity(rs.getString("city"));
        location.setPostalCode(rs.getString("postal_code"));

        State state = new State();
        state.setStateId(rs.getInt("state_id"));
        state.setStateName(rs.getString("state_name"));
        state.setStateAbbrv(rs.getString("state_abbrv"));
        location.setState(state);

        return location;
    }
}