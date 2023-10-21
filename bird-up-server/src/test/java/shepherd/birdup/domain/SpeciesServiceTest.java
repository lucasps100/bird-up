package shepherd.birdup.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shepherd.birdup.data.SpeciesRepository;
import shepherd.birdup.models.Species;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SpeciesServiceTest {

    @MockBean
    SpeciesRepository repository;

    @Autowired
    SpeciesService service;

    @Test
    void shouldFindAll() {
        when(repository.findAll()).thenReturn(List.of(new Species(12, "Duck", "Duckington")));
        assertEquals(List.of(new Species(12, "Duck", "Duckington")), service.findAll());
    }

    @Test
    void shouldFindByPartialName() {
        when(repository.findByPartialName("duc")).thenReturn(List.of(new Species(12, "Duck", "Duckington")));
        assertEquals(List.of(new Species(12, "Duck", "Duckington")), service.findByPartialName("duc"));
    }

    @Test
    void shouldFindByShortName() {
        when(repository.findByShortName("duck")).thenReturn(new Species(12, "Duck", "Duckington"));
        assertEquals(new Species(12, "Duck", "Duckington"), service.findByShortName("duck"));
    }

    @Test
    void shouldFindById() {
        when(repository.findById(anyInt())).thenReturn(new Species(12, "Duck", "Duckington"));
        assertEquals(new Species(12, "Duck", "Duckington"), service.findById(12));
    }
}
