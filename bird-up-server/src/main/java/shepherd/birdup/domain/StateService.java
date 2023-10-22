package shepherd.birdup.domain;

import org.springframework.stereotype.Service;
import shepherd.birdup.data.StateRepository;
import shepherd.birdup.models.State;

import java.util.List;

@Service
public class StateService {
    private final StateRepository repository;

    public StateService(StateRepository repository) {
        this.repository = repository;
    }

    public List<State> findAll() {
        return repository.findAll();
    }

    public State findByStateId(int stateId) {
        return repository.findStateById(stateId);
    }

    public State findByStateAbbrv(String stateAbbrv) {
        return repository.findStateByAbbrv(stateAbbrv);
    }
}
