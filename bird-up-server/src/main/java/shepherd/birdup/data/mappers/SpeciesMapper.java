package shepherd.birdup.data.mappers;

import org.springframework.jdbc.core.RowMapper;
import shepherd.birdup.models.Species;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpeciesMapper implements RowMapper<Species> {
    @Override
    public Species mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
