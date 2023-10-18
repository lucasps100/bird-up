package shepherd.birdup.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shepherd.birdup.data.mappers.StateMapper;
import shepherd.birdup.models.State;

import java.util.List;

@Repository
public class StateJdbcTemplateRepository {

    private final JdbcTemplate jdbcTemplate;

    public StateJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<State> findAll() {
        final String sql = """
                select * from state;
                """;
        return jdbcTemplate.query(sql, new StateMapper());
    }


    public State findStateByAbbrv(String stateAbbrv){
        final String sql = """
                select * from state
                where state_abbrv = ?;
                """;
        return jdbcTemplate.query(sql, new StateMapper(), stateAbbrv)
                .stream().findFirst().orElse(null);

    }
}
