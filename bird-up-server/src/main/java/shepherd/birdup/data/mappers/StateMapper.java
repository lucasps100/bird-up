package shepherd.birdup.data.mappers;

import org.springframework.jdbc.core.RowMapper;
import shepherd.birdup.models.State;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StateMapper implements RowMapper<State> {
    @Override
    public State mapRow(ResultSet rs, int rowNum) throws SQLException {
        State state = new State();
        state.setStateId(rs.getInt("state_id"));
        state.setStateName(rs.getString("state_name"));
        state.setStateAbbrv(rs.getString("state_abbrv"));
        return state;
    }
}
