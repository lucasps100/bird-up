package shepherd.birdup.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shepherd.birdup.TestHelper;
import shepherd.birdup.data.LikeRepository;
import shepherd.birdup.data.PostRepository;
import shepherd.birdup.data.ProfileRepository;
import shepherd.birdup.models.Like;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LikeServiceTest {

    @MockBean
    LikeRepository likeRepository;

    @MockBean
    PostRepository postRepository;

    @MockBean
    ProfileRepository profileRepository;

    @Autowired
    LikeService service;

    TestHelper th = new TestHelper();

    @Test
    void shouldFindOneLikeForPost() {
        when(likeRepository.findByPostId(anyInt())).thenReturn(List.of(th.createLike(1)));

        List<Like> actual = service.findByPostId(1);

        assertEquals(List.of(th.createLike(1)), actual);

    }

    @Test
    void shouldNotCreateNull() {
        when(likeRepository.create(any())).thenReturn(th.createLike(2));
        Result<?> actual = service.create(null);
        System.out.println(actual.getMessages());
        assertEquals(1, actual.getMessages().size());
        assertEquals("nothing to add", actual.getMessages().get(0));
    }

    @Test
    void shouldNotLikeNonExistentPost() {
        when(likeRepository.create(any())).thenReturn(th.createLike(2));
        when(postRepository.findByPostId(anyInt())).thenReturn(null);
        when(profileRepository.findById(anyInt())).thenReturn(th.createProfile(1));
        when(likeRepository.findByPostId(anyInt())).thenReturn(List.of());
        Like arg = th.createLike(10);
        Result<?> actual = service.create(arg);
        System.out.println(actual.getMessages());
        assertEquals(1, actual.getMessages().size());
        assertEquals("post must exist", actual.getMessages().get(0));
    }

    @Test
    void shouldNotDuplicateLike() {
        when(likeRepository.create(any())).thenReturn(th.createLike(2));
        when(postRepository.findByPostId(anyInt())).thenReturn(th.createPost(1));
        when(profileRepository.findById(anyInt())).thenReturn(th.createProfile(1));
        when(likeRepository.findByPostId(anyInt())).thenReturn(List.of(th.createLike(1)));
        Like arg = th.createLike(2);
        Result<?> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("user already liked this post", actual.getMessages().get(0));
    }

    @Test
    void shouldNotLikeWithNonexistentProfile() {
        when(likeRepository.create(any())).thenReturn(th.createLike(2));
        when(postRepository.findByPostId(anyInt())).thenReturn(th.createPost(1));
        when(profileRepository.findById(anyInt())).thenReturn(null);
        when(likeRepository.findByPostId(anyInt())).thenReturn(List.of());

        Like arg = th.createLike(1);
        Result<?> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("liker profile must exist", actual.getMessages().get(0));
    }

    @Test
    void shouldCreate() {
        when(likeRepository.create(any())).thenReturn(th.createLike(2));
        when(postRepository.findByPostId(anyInt())).thenReturn(th.createPost(1));
        when(profileRepository.findById(anyInt())).thenReturn(th.createProfile(1));
        when(likeRepository.findByPostId(anyInt())).thenReturn(List.of());

        Like arg = th.createLike(1);
        assertTrue(service.create(arg).isSuccess());
    }

    @Test
    void shouldDelete() {
        when(likeRepository.deleteByIds(anyInt(), anyInt())).thenReturn(true);
        Result<?> actual = service.deletebyIds(1, 1);
        assertTrue(actual.isSuccess());

    }

    @Test
    void shouldNotDelete() {
        when(likeRepository.deleteByIds(anyInt(), anyInt())).thenReturn(false);
        Result<?> actual = service.deletebyIds(1, 1);
        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
        assertEquals("User 1 never liked post 1", actual.getMessages().get(0));
    }

}
