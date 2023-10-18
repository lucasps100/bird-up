package shepherd.birdup.data.mappers;

import org.springframework.jdbc.core.RowMapper;
import shepherd.birdup.models.Species;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpeciesMapper implements RowMapper<Species> {
    @Override
    public Species mapRow(ResultSet rs, int rowNum) throws SQLException {
        Species species = new Species();
        species.setSpeciesId(rs.getInt("species_id"));
        species.setSpeciesShortName(rs.getString("species_short_name"));
        species.setSpeciesLongName(rs.getString("species_long_name"));
        return species;
    }
}
