package shepherd.birdup.data;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shepherd.birdup.models.Species;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)

public class SpeciesRepositoryTest {

    @Autowired
    SpeciesJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Species> actual = repository.findAll();
        assertEquals(actual.size(), 3);
    }

    @Test
    void shouldFindByPartialName() {
        List<Species> actual = repository.findByPartialName("rat");
        assertEquals(actual.size(), 1);
        assertEquals(actual.get(0).getSpeciesShortName(), "Seagull");
    }

    @Test
    void shouldNotFindByInvalidPartialName() {
        List<Species> actual = repository.findByPartialName("fnhbsjdnjs");
        assertEquals(actual.size(), 0);
    }

    @Test
    void shouldFindByShortName() {
        Species actual = repository.findByShortName("pigEon");
        assertNotNull(actual);
        assertEquals(actual.getSpeciesLongName(), "Pigeus Maximus");
    }

    @Test
    void shouldNotFindByInvalidShortName() {
        Species actual = repository.findByShortName("pig");
        assertNull(actual);
    }
}
