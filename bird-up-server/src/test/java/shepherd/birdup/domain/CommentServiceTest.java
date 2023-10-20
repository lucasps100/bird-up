package shepherd.birdup.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shepherd.birdup.TestHelper;
import shepherd.birdup.data.CommentRepository;
import shepherd.birdup.data.PostRepository;
import shepherd.birdup.data.ProfileRepository;
import shepherd.birdup.models.Comment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommentServiceTest {

    @MockBean
    CommentRepository commentRepository;

    @MockBean
    PostRepository postRepository;

    @MockBean
    ProfileRepository profileRepository;

    @Autowired
    CommentService service;

    TestHelper th = new TestHelper();

    @Test
    void shouldFindById() {
        when(commentRepository.findById(1)).thenReturn(th.createComment(1, 1));
        assertEquals(th.createComment(1, 1), service.findById(1));
    }

    @Test
    void shouldNotFindById(){
        when(commentRepository.findById(1)).thenReturn(null);
        assertNull(service.findById(1));
    }

    @Test
    void shouldFindByPostId() {
        when(commentRepository.findByPostId(anyInt())).thenReturn(List.of(th.createComment(1, 1)));
        assertEquals(List.of(th.createComment(1, 1)), service.findByPostId(1));
    }

    @Test
    void shouldNotFindByPostId(){
        when(commentRepository.findByPostId(anyInt())).thenReturn(List.of());
        assertEquals(List.of(), service.findByPostId(1));
    }

    @Test
    void shouldCreate() {
        when(commentRepository.create(any())).thenReturn(th.createComment(1,1));
        when(profileRepository.findById(anyInt())).thenReturn(th.createProfile(1));
        when(postRepository.findByPostId(anyInt())).thenReturn(th.createPost(1));
        Comment arg = th.createComment(0,2);
        Result<Comment> actual = service.create(arg);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotCreateNull(){
        Result<Comment> actual = service.create(null);
        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
        assertEquals("nothing to add", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithNonexistentCommenter() {
        when(commentRepository.create(any())).thenReturn(th.createComment(1,1));
        when(profileRepository.findById(anyInt())).thenReturn(null);
        when(postRepository.findByPostId(anyInt())).thenReturn(th.createPost(1));
        Comment arg = th.createComment(0,2);
        Result<Comment> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("commenter profile must exist", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateCommentWithNoText() {
        when(commentRepository.create(any())).thenReturn(th.createComment(1,1));
        when(profileRepository.findById(anyInt())).thenReturn(th.createProfile(1));
        when(postRepository.findByPostId(anyInt())).thenReturn(th.createPost(1));
        Comment arg = th.createComment(0,2);
        arg.setCommentText("");
        Result<Comment> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("comment text is required", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateCommentWithNonexistentPost() {
        when(commentRepository.create(any())).thenReturn(th.createComment(1,1));
        when(profileRepository.findById(anyInt())).thenReturn(th.createProfile(1));
        when(postRepository.findByPostId(anyInt())).thenReturn(null);
        Comment arg = th.createComment(0,2);
        Result<Comment> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("post must exist", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateCommentWithPresetId() {
        when(commentRepository.create(any())).thenReturn(th.createComment(1,1));
        when(profileRepository.findById(anyInt())).thenReturn(th.createProfile(1));
        when(postRepository.findByPostId(anyInt())).thenReturn(th.createPost(1));
        Comment arg = th.createComment(1,2);
        Result<Comment> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("comment id should not be set", actual.getMessages().get(0));
    }

    // test update and delete




}
