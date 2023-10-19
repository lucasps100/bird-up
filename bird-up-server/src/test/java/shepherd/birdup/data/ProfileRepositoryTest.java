package shepherd.birdup.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shepherd.birdup.TestHelper;
import shepherd.birdup.models.Profile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ProfileRepositoryTest {

    @Autowired
    ProfileJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    TestHelper th = new TestHelper();

    int NEXT_ID = 4;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindByUsername() {
        Profile actual = repository.findByUsername("jane@doe.com");
        assertEquals(actual.getAppUserId(), 1);
    }

    @Test
    void shouldNotFindByInvalidUsername() {
        assertNull(repository.findByUsername("fkjdnjs"));
    }

    @Test
    void shouldFindByPartialName() {
        List<Profile> actual = repository.findByPartialName("doe");
        assertEquals(1, actual.size());
        assertEquals("Jane", actual.get(0).getFirstName());
    }

    @Test
    void shouldNotFindByInvalidPartialName() {
        assertEquals(0, repository.findByPartialName("dkmgnsskdmg").size());
    }

    @Test
    void shouldCreate() {
        Profile arg = th.createProfile(NEXT_ID);
        Profile actual = repository.createProfile(arg);
        assertEquals(arg, actual);
    }

    @Test
    void shouldUpdate() {
        Profile arg = th.createProfile(1);
        assertTrue(repository.updateProfile(arg));
        assertEquals("John", repository.findByUsername("jane@doe.com").getFirstName());
    }

    @Test
    void shouldNotUpdate() {
        Profile arg = th.createProfile(34);
        assertFalse(repository.updateProfile(arg));
    }

}
