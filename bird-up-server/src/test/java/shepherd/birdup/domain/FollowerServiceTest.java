package shepherd.birdup.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shepherd.birdup.TestHelper;
import shepherd.birdup.data.FollowerRepository;
import shepherd.birdup.data.ProfileRepository;
import shepherd.birdup.models.Follower;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FollowerServiceTest {

    @MockBean
    FollowerRepository followerRepository;

    @MockBean
    ProfileRepository profileReposiory;

    @Autowired
    FollowerService service;

    TestHelper th = new TestHelper();

    @Test
    void shouldFindByIds() {
        when(followerRepository.findByIds(anyInt(), anyInt())).thenReturn(th.createFollower(1));
        assertEquals(service.findByIds(1, 2), th.createFollower(1));
    }

    @Test
    void shouldCreate() {
        when(profileReposiory.findById(anyInt())).thenReturn(th.createProfile(1));
        when(followerRepository.create(any())).thenReturn(th.createFollower(1));
        Follower arg = th.createFollower(2);
        assertTrue(service.create(arg).isSuccess());
    }

    @Test
    void shouldNotCreateNull() {
        when(profileReposiory.findById(anyInt())).thenReturn(th.createProfile(1));
        when(followerRepository.create(any())).thenReturn(th.createFollower(1));
        Result<Follower> actual = service.create(null);
        assertEquals(1, actual.getMessages().size());
        assertEquals("nothing to add", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateFollowWithNullFollower() {
        when(profileReposiory.findById(2)).thenReturn(th.createProfile(2));
        when(followerRepository.create(any())).thenReturn(th.createFollower(1));
        Follower arg = th.createFollower(1);
        Result<Follower> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("follower profile must exist", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateFollowWithNullFollowee() {
        when(profileReposiory.findById(1)).thenReturn(th.createProfile(1));
        when(followerRepository.create(any())).thenReturn(th.createFollower(1));
        Follower arg = th.createFollower(1);
        Result<Follower> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("followed profile must exist", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateNonDistinctFollow() {
        when(profileReposiory.findById(anyInt())).thenReturn(th.createProfile(1));
        when(followerRepository.create(any())).thenReturn(th.createFollower(1));
        when(followerRepository.findByIds(anyInt(), anyInt())).thenReturn(th.createFollower(1));
        Follower arg = th.createFollower(1);
        Result<Follower> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("user already follows this profile", actual.getMessages().get(0));
    }

    @Test
    void shouldNotFollowSelf() {
        when(profileReposiory.findById(anyInt())).thenReturn(th.createProfile(1));
        when(followerRepository.create(any())).thenReturn(th.createFollower(1));
        Follower arg = th.createFollower(1);
        arg.getFollowee().setAppUserId(1);
        Result<Follower> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("users may not follow themselves", actual.getMessages().get(0));
    }

    @Test
    void shouldDelete() {
        when(followerRepository.deleteByIds(anyInt(), anyInt())).thenReturn(true);
        assertTrue(service.deleteByIds(1,2).isSuccess());
    }

    @Test
    void shouldNotDelete() {
        when(followerRepository.deleteByIds(anyInt(), anyInt())).thenReturn(false);
        assertFalse(service.deleteByIds(1, 2).isSuccess());
    }

}
