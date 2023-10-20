package shepherd.birdup.data;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shepherd.birdup.models.State;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest

public class StateRepositoryTest {

    @Autowired
    StateJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<State> actual = repository.findAll();
        assertEquals(actual.size(), 50);
    }

    @Test
    void shouldFindByStateAbbrv() {
        State actual = repository.findStateByAbbrv("AL");
        assertEquals(actual, new State(1, "Alabama", "AL"));
    }

    @Test
    void shouldNotFindStateByInvalidStateAbbrv() {
        State actual = repository.findStateByAbbrv("JW");
        assertNull(actual);
    }
}
