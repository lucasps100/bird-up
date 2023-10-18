package shepherd.birdup.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shepherd.birdup.TestHelper;
import shepherd.birdup.models.Like;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LikeRepositoryTest {

    @Autowired
    LikeJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    TestHelper th = new TestHelper();

    @BeforeEach
    void setup(){
        knownGoodState.set();
    }

    @Test
    void shouldFindByPostId() {
        List<Like> actual = repository.findByPostId(3);
        assertEquals(actual.size(), 1);
        Like expected = th.createLike(3);
        assertEquals(actual.get(0), expected);
    }

    @Test
    void shouldNotFindBYInvalidPostId() {
        List<Like> actual = repository.findByPostId(2);
        assertEquals(actual.size(), 0);
    }

    @Test
    void shouldCreateLike() {
        Like arg = th.createLike(2);
        Like actual = repository.create(arg);
        assertNotNull(actual);
        assertEquals(arg, repository.findByPostId(2).get(0));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteByIds(1,3));
        assertEquals(0, repository.findByPostId(3).size());
    }

    @Test
    void shouldNotDeleteByInvalidIds() {
        assertFalse(repository.deleteByIds(3, 3));
    }
}
