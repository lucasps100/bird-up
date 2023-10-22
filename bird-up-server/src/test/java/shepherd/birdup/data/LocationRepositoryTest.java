package shepherd.birdup.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shepherd.birdup.TestHelper;
import shepherd.birdup.models.Location;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LocationRepositoryTest {

    int NEXT_ID = 4;

    TestHelper th = new TestHelper();
    @Autowired
    LocationJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Location> actual = repository.findAll();
        assertEquals(actual.size(), 3);
        System.out.println(actual);
    }

    @Test
    void shouldFindById() {
        Location actual = repository.findById(1);
        assertNotNull(actual);
        assertEquals(actual.getCity(), "Las Vegas");
    }

    @Test
    void shouldNotFindByInvalidId() {
        Location actual = repository.findById(99);
        assertNull(actual);
    }

    @Test
    void shouldFindByStateAbbrv() {
        List<Location> actual = repository.findByStateAbbrv("ME");
        assertEquals(actual.size(), 1);
        assertEquals(actual.get(0).getCity(), "Kennebunk");
    }

    @Test
    void shouldNotFindBYInvalidStateAbbrv() {
        List<Location> actual = repository.findByStateAbbrv("JW");
        assertEquals(actual.size(), 0);
    }

    @Test
    void shouldFindByPartialName() {
        List<Location> actual = repository.findByPartialName("ne");
        assertEquals(2, actual.size());
        assertTrue(actual.get(0).getCity().equals("Kennebunk") || actual.get(1).getCity().equals("Kennebunk"));
    }

    @Test
    void shouldCreate() {
        Location arg = th.createLocation(0);
        Location actual = repository.create(arg);
        arg.setLocationId(NEXT_ID);
        assertEquals(actual, arg);
        actual = repository.findById(NEXT_ID);
        assertEquals(actual, arg);
    }

    @Test
    void shouldUpdate() {
        Location arg = th.createLocation(1);
        assertTrue(repository.update(arg));
        assertEquals(arg, repository.findById(1));
    }

    @Test
    void shouldNotUpdateInvalidId() {
        Location arg = th.createLocation(99);
        assertFalse(repository.update(arg));
        assertNull(repository.findById(99));
    }

    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteById(3));
        assertEquals(repository.findAll().size(), 2);

    }

    @Test
    void shouldNotDeleteByInvalidId() {
        assertFalse(repository.deleteById(99));
    }


}
