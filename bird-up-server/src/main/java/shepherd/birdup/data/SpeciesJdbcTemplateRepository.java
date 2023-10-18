package shepherd.birdup.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shepherd.birdup.data.mappers.SpeciesMapper;
import shepherd.birdup.models.Species;

import java.util.List;

@Repository
public class SpeciesJdbcTemplateRepository implements SpeciesRepository {

    private final JdbcTemplate jdbcTemplate;

    public SpeciesJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Species> findAll() {
        final String sql = """
                select * from species;
                """;

        return jdbcTemplate.query(sql, new SpeciesMapper());
    }

    @Override
    public List<Species> findByPartialName(String partialName){
        final String sql = """
                select * from species
                where species_short_name like ?
                OR species_long_name like ?;
                """;

        return jdbcTemplate.query(sql, new SpeciesMapper(), "%" +partialName + "%",
                "%" +partialName + "%");
    }


    @Override
    public Species findByShortName(String speciesShortName) {
        final String sql = """
                select * from species
                where species_short_name like ?;
                """;

        return jdbcTemplate.query(sql, new SpeciesMapper(), speciesShortName).stream()
                .findFirst().orElse(null);
    }
}
