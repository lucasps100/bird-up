package shepherd.birdup.data;

import shepherd.birdup.models.State;

import java.util.List;

public interface StateRepository {
    List<State> findAll();

    State findStateByAbbrv(String stateAbbrv);
}
