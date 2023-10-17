package shepherd.birdup.data;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shepherd.birdup.data.mappers.LocationMapper;
import shepherd.birdup.models.Location;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class LocationJdbcTemplateRepository {
    private final JdbcTemplate jdbcTemplate;
    public LocationJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    List<Location> findAll() {
        final String sql = "select location_id, city, postal_code, state_abbrv, l.state_id, state_name"
                + "from location l" +
                "join state on location.state_id = state.state_id;";
        return jdbcTemplate.query(sql, new LocationMapper());
    }

    @Transactional
    Location findById(int locationId) {
        final String sql = "select location_id, city, postal_code, state_abbrv, l.state_id, state_name"
                + "from location l" +
                "join state on location.state_id = state.state_id"
                + "where location_id = ?;";

        return jdbcTemplate.query(sql, new LocationMapper(), locationId).stream()
                .findFirst()
                .orElse(null);

    }
    @Transactional
    List<Location> findByStateAbbv(String stateAbbv) {
        final String sql = "select location_id, city, postal_code, state_abbrv, l.state_id, state_name"
                + "from location l" +
                "join state on location.state_id = state.state_id"
                + "where state.state_abbv = ?;";

        return jdbcTemplate.query(sql, new LocationMapper(), stateAbbv);

    }


    public Location add(Location location) {

        final String sql = "insert into location (city, postal_code, state_id)"
                + "values (?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, location.getCity());
            ps.setInt(2, location.getPostalCode());
            ps.setInt(3, location.getState().getStateId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        location.setLocationId(keyHolder.getKey().intValue());
        return location;
    }

    public boolean update(Location location) {

        final String sql = "update location set "
                + "city = ?, "
                + "postal_code = ?, "
                + "state_id = ?"
                + "where location_id = ?;";

        return jdbcTemplate.update(sql,
                location.getCity(),
                location.getPostalCode(),
                location.getState().getStateId(),
                location.getLocationId()) > 0;
    }

    public boolean deleteById(int locationId) {
        return jdbcTemplate.update(
                "delete from location where location_id = ?", locationId) > 0;
    }
}
