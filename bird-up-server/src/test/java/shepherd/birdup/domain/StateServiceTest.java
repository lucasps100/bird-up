package shepherd.birdup.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shepherd.birdup.data.StateRepository;
import shepherd.birdup.models.State;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StateServiceTest {
    @MockBean
    StateRepository repository;

    @Autowired
    StateService service;

    @Test
    void shouldFindAll() {
        when(repository.findAll()).thenReturn(List.of(new State(12, "Maine", "ME")));
        assertEquals(List.of(new State(12, "Maine", "ME")), service.findAll());
    }

    @Test
    void shouldFindById() {
        when(repository.findStateById(anyInt())).thenReturn(new State(12, "Maine", "ME"));
        assertEquals(new State(12, "Maine", "ME"), service.findByStateId(2));
    }

    @Test
    void shouldFindByStateAbbrv() {
        when(repository.findStateByAbbrv(any())).thenReturn(new State(12, "Maine", "ME"));
        assertEquals(new State(12, "Maine", "ME"), service.findByStateAbbrv("ME"));

    }
}
