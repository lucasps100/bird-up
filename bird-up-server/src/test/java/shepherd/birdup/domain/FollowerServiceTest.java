package shepherd.birdup.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import shepherd.birdup.TestHelper;
import shepherd.birdup.data.FollowerRepository;
import shepherd.birdup.data.ProfileRepository;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

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
        when(followerRepository.findByIds(anyInt(), anyInt())).thenReturn()
    }
}
