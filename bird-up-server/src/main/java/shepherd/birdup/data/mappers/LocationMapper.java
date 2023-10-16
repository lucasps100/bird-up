package shepherd.birdup.data.mappers;

import org.springframework.jdbc.core.RowMapper;
import shepherd.birdup.models.Location;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationMapper implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet resultSet, int i) throws SQLException {
        Location location = new Location();
        location.setLocationId(resultSet.getInt("location_id"));
        location.setCity(resultSet.getString("city"));
        location.setPostalCode(resultSet.getInt("postal_code"));
        return location;
    }
}