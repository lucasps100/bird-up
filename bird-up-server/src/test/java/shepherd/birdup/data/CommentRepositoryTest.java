package shepherd.birdup.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shepherd.birdup.TestHelper;
import shepherd.birdup.models.Comment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentRepositoryTest {

    int NEXT_ID = 4;
    @Autowired
    CommentJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    TestHelper th = new TestHelper();

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindByPostId() {
        List<Comment> actual = repository.findByPostId(2);
        assertEquals(2, actual.size());
        assertEquals(th.createComment(1, 2), actual.get(0));
    }

    @Test
    void shouldNotFindByDeletedPostId() {
        List<Comment> actual = repository.findByPostId(1);
        assertEquals(0, actual.size());
    }

    @Test
    void shouldNotFindByInvalidPostId() {
        List<Comment> actual = repository.findByPostId(33);
        assertEquals(0, actual.size());
    }

    @Test
    void shouldCreateComment() {
        Comment arg = th.createComment(0, 2);
        Comment actual = repository.create(arg);
        arg.setCommentId(NEXT_ID);
        assertEquals(arg, actual);
    }

    @Test
    void shouldUpdateComment() {
        Comment arg = th.createComment(1, 2);
        arg.setCommentText("updated comment");
        assertTrue(repository.update(arg));
        assertEquals(arg, repository.findByPostId(2).get(0));
    }

    @Test
    void shouldNotUpdateCommentWithInvalidId() {
        Comment arg = th.createComment(27, 3);
        assertFalse(repository.update(arg));
        assertEquals(0, repository.findByPostId(3).size());
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteByCommentId(1));
        assertEquals(repository.findByPostId(2).size(), 1);
    }

    @Test
    void shouldNotDeleteByInvalidId() {
        assertFalse(repository.deleteByCommentId(33));
    }

}
