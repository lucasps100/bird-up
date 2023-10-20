package shepherd.birdup.data;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shepherd.birdup.TestHelper;
import shepherd.birdup.models.Post;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PostRepositoryTest {


    int NEXT_ID = 4;
    @Autowired
    PostJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    TestHelper th = new TestHelper();

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllEnabled() {
        List<Post> actual = repository.findAll();
        assertEquals(2, actual.size());
        assertEquals(3, actual.get(0).getPostId());
    }

    @Test
    void shouldFindByAppUserId() {
        List<Post> actual = repository.findByAppUserId(1);
        assertEquals(1, actual.size());
        assertEquals(2, actual.get(0).getPostId());
    }

    @Test
    void shouldFindByStateAbbrv() {
        List<Post> actual = repository.findByStateAbbrv("SC");
        assertEquals(2, actual.size());
        assertEquals(3, actual.get(0).getPostId());
    }

    @Test
    void shouldFindBySpeciesShortName() {
        List<Post> actual = repository.findBySpeciesShortName("pigeon");
        assertEquals(1, actual.size());
        assertEquals(2, actual.get(0).getPostId());
    }

    @Test
    void shouldFindByPostalCode() {
        List<Post> actual = repository.findByPostalCode("29926");
        assertEquals(2, actual.size());
        assertEquals(3, actual.get(0).getPostId());
    }

    @Test
    void shouldFindByLikerId() {
        List<Post> actual = repository.findLikedPostsByLikerId(1);
        assertEquals(1, actual.size());
        assertEquals(3, actual.get(0).getPostId());
    }

    @Test
    void shouldFindByFollowerId() {
        List<Post> actual = repository.findFolloweePostsByFollowerId(1);
        assertEquals(1, actual.size());
        assertEquals(3, actual.get(0).getPostId());
    }

    @Test
    void shouldFindByCityAndStateAbbrv() {
        List<Post> actual = repository.findByCityAndStateAbbrv("Hilton Head Island", "SC");
        assertEquals(2, actual.size());
    }

    @Test
    void shouldCreate() {
        Post arg = th.createPost(0);
        Post actual = repository.create(arg);
        arg.setPostId(NEXT_ID);
        assertEquals(arg, actual);
    }

    @Test
    void shouldUpdate() {
        Post arg = th.createPost(2);
        assertTrue(repository.update(arg));
        System.out.println(arg);
        System.out.println(repository.findByPostId(2));
        assertEquals(arg.getPostText(), repository.findByPostId(2).getPostText());
        assertEquals(arg.getPostLocation(), repository.findByPostId(2).getPostLocation());
    }

    @Test
    void shouldNotUpdateInvalidId() {
        Post arg = th.createPost(49);
        assertFalse(repository.update(arg));
        assertNull(repository.findByPostId(49));
    }

    @Test
    void shouldDisable() {
        assertTrue(repository.softDeleteById(2));
        assertNull(repository.findByPostId(2));
    }

    @Test
    void shouldNotDisableInvalidPost() {
        assertFalse(repository.softDeleteById(4));
    }

}
