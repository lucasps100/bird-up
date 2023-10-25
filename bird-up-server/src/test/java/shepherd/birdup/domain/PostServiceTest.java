package shepherd.birdup.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shepherd.birdup.TestHelper;
import shepherd.birdup.data.LocationRepository;
import shepherd.birdup.data.PostRepository;
import shepherd.birdup.data.ProfileRepository;
import shepherd.birdup.data.SpeciesRepository;
import shepherd.birdup.models.Post;
import shepherd.birdup.models.Species;

import java.sql.Blob;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PostServiceTest {

    @MockBean
    PostRepository repository;

    @MockBean
    ProfileRepository profileRepository;

    @MockBean
    LocationRepository locationRepository;

    @MockBean
    SpeciesRepository speciesRepository;

    @Autowired
    PostService service;

    TestHelper th = new TestHelper();

    @Test
    void shouldFindAll() {
        when(repository.findAll()).thenReturn(List.of(th.createPost(1)));
        assertEquals(List.of(th.createPost(1)), service.findAll());
    }

    @Test
    void shouldFindByAppUserId(){
        when(repository.findByAppUserId(1)).thenReturn(List.of(th.createPost(1)));
        assertEquals(List.of(th.createPost(1)), service.findByAppUserId(1));
    }

//    @Test
//    void shouldFindByStateAbbrv() {
//        when(repository.findByStateAbbrv("ME")).thenReturn(List.of(th.createPost(1)));
//        assertEquals(List.of(th.createPost(1)), service.findByStateAbbrv("ME"));
//    }

    @Test
    void shouldFindBySpeciesShortName() {
        when(repository.findBySpeciesShortName(any())).thenReturn(List.of(th.createPost(1)));
        assertEquals(List.of(th.createPost(1)), service.findBySpeciesShortName("duck"));
    }

//    @Test
//    void shouldFindByPostalCode() {
//        when(repository.findByPostalCode(any())).thenReturn(List.of(th.createPost(1)));
//        assertEquals(List.of(th.createPost(1)), service.findByPostalCode("11111"));
//    }

    @Test
    void shouldFindByLikerId() {
        when(repository.findLikedPostsByLikerId(anyInt())).thenReturn(List.of(th.createPost(1)));
        assertEquals(List.of(th.createPost(1)), service.findLikedPostsByLikerId(1));
    }

    @Test
    void shouldFindByFollowerId() {
        when(repository.findFolloweePostsByFollowerId(anyInt())).thenReturn(List.of(th.createPost(1)));
        assertEquals(List.of(th.createPost(1)), service.findFolloweePostsByFollowerId(1));
    }

//    @Test
//    void shouldFindByCityAndState() {
//        when(repository.findByCityAndStateAbbrv(any(), any())).thenReturn(List.of(th.createPost(1)));
//        assertEquals(List.of(th.createPost(1)), service.findByCityAndStateAbbrv("Old Town", "ME"));
//    }

    @Test
    void shouldFindById() {
        when(repository.findByPostId(anyInt())).thenReturn(th.createPost(1));
        assertEquals(th.createPost(1), service.findByPostId(1));
    }

    @Test
    void shouldCreate() {
        when(repository.create(any())).thenReturn(th.createPost(1));
        when(locationRepository.findById(anyInt())).thenReturn(th.createLocation(1));
        when(profileRepository.findById(anyInt())).thenReturn(th.createProfile(1));
        when(speciesRepository.findById(anyInt())).thenReturn(new Species(1, "Duck", "Ducksworth"));
        Post arg = th.createPost(1);
        arg.setPostId(0);
        Result<Post> actual = service.create(arg);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotCreateWithPresetId() {
        when(repository.create(any())).thenReturn(th.createPost(1));
        when(locationRepository.findById(anyInt())).thenReturn(th.createLocation(1));
        when(profileRepository.findById(anyInt())).thenReturn(th.createProfile(1));
        when(speciesRepository.findById(anyInt())).thenReturn(new Species(1, "Duck", "Ducksworth"));
        Post arg = th.createPost(1);
        Result<Post> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("post id should not be set", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithNonexistentLocation() {
        when(repository.create(any())).thenReturn(th.createPost(1));
        when(locationRepository.findById(anyInt())).thenReturn(null);
        when(profileRepository.findById(anyInt())).thenReturn(th.createProfile(1));
        when(speciesRepository.findById(anyInt())).thenReturn(new Species(1, "Duck", "Ducksworth"));
        Post arg = th.createPost(1);
        arg.setPostId(0);
        Result<Post> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("location must exist", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithNonexistentSpecies() {
        when(repository.create(any())).thenReturn(th.createPost(1));
        when(locationRepository.findById(anyInt())).thenReturn(th.createLocation(1));
        when(profileRepository.findById(anyInt())).thenReturn(th.createProfile(1));
        when(speciesRepository.findById(anyInt())).thenReturn(null);
        Post arg = th.createPost(1);
        arg.setPostId(0);
        Result<Post> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("species must exist", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithNonexistentPoster() {
        when(repository.create(any())).thenReturn(th.createPost(1));
        when(locationRepository.findById(anyInt())).thenReturn(th.createLocation(1));
        when(profileRepository.findById(anyInt())).thenReturn(null);
        when(speciesRepository.findById(anyInt())).thenReturn(new Species(1, "Duck", "Ducksworth"));
        Post arg = th.createPost(1);
        arg.setPostId(0);
        Result<Post> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("poster profile must exist", actual.getMessages().get(0));
    }

    @Test
    void shouldUpdate() {
        when(repository.update(any())).thenReturn(true);
        when(locationRepository.findById(anyInt())).thenReturn(th.createLocation(1));
        when(profileRepository.findById(anyInt())).thenReturn(th.createProfile(1));
        when(speciesRepository.findById(anyInt())).thenReturn(new Species(1, "Duck", "Ducksworth"));
        Post arg = th.createPost(1);
        Result<Post> actual = service.update(arg);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateToNull() {
        Result<Post> actual = service.update(null);
        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
    }

    @Test
    void shouldNotUpdateWithZeroId() {
        when(repository.update(any())).thenReturn(true);
        when(locationRepository.findById(anyInt())).thenReturn(th.createLocation(1));
        when(profileRepository.findById(anyInt())).thenReturn(th.createProfile(1));
        when(speciesRepository.findById(anyInt())).thenReturn(new Species(1, "Duck", "Ducksworth"));
        Post arg = th.createPost(0);
        Result<Post> actual = service.update(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("post id is required", actual.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWithInvalidId() {
        when(repository.update(any())).thenReturn(false);
        when(locationRepository.findById(anyInt())).thenReturn(th.createLocation(1));
        when(profileRepository.findById(anyInt())).thenReturn(th.createProfile(1));
        when(speciesRepository.findById(anyInt())).thenReturn(new Species(1, "Duck", "Ducksworth"));
        Post arg = th.createPost(4);
        Result<Post> actual = service.update(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("post 4 not found", actual.getMessages().get(0));
    }

    @Test
    void shouldSoftDelete() {
        when(repository.softDeleteById(anyInt())).thenReturn(true);
        assertTrue(service.softDeleteById(1).isSuccess());
    }

    @Test
    void shouldNotSoftDelete() {
        when(repository.softDeleteById(anyInt())).thenReturn(false);
        Result<Post> actual = service.softDeleteById(1);
        assertEquals(1, actual.getMessages().size());
        assertEquals("post 1 not found", actual.getMessages().get(0));

    }




}
