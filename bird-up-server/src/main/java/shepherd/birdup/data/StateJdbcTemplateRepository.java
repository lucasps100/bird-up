package shepherd.birdup.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shepherd.birdup.data.mappers.StateMapper;
import shepherd.birdup.models.State;

import java.util.List;

@Repository
public class StateJdbcTemplateRepository implements StateRepository {

    private final JdbcTemplate jdbcTemplate;

    public StateJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<State> findAll() {
        final String sql = """
                select * from state;
                """;
        return jdbcTemplate.query(sql, new StateMapper());
    }

    @Override
    public State findStateById(int stateId) {
        final String sql = """
                select * from state
                where state_id = ?;
                """;
        return jdbcTemplate.query(sql, new StateMapper(), stateId)
                .stream().findFirst().orElse(null);
    }


    @Override
    public State findStateByAbbrv(String stateAbbrv) {
        final String sql = """
                select * from state
                where state_abbrv = ?;
                """;
        return jdbcTemplate.query(sql, new StateMapper(), stateAbbrv)
                .stream().findFirst().orElse(null);

    }
}
