package shepherd.birdup.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shepherd.birdup.TestHelper;
import shepherd.birdup.data.AppUserRepository;
import shepherd.birdup.data.ProfileRepository;
import shepherd.birdup.models.AppUser;
import shepherd.birdup.models.Profile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProfileServiceTest {

    @MockBean
    ProfileRepository repository;

    @MockBean
    AppUserRepository appUserRepository;

    @Autowired
    ProfileService service;

    TestHelper th = new TestHelper();

    @Test
    void shouldFindById() {
        when(repository.findById(anyInt())).thenReturn(th.createProfile(1));
        assertEquals(service.findById(1), th.createProfile(1));
    }

    @Test
    void shouldFindByUsername() {
        when(repository.findByUsername(any())).thenReturn(th.createProfile(1));
        assertEquals(service.findByUsername("lucas@shep.com"), th.createProfile(1));
    }

    @Test
    void shouldFindByPartialName(){
        when(repository.findByPartialName(any())).thenReturn(List.of(th.createProfile(1), th.createProfile(2)));
        assertEquals(2, service.findByPartialName("jdns").size());
    }

    @Test
    void shouldCreate(){
        when(repository.createProfile(any())).thenReturn(th.createProfile(1));
        when(appUserRepository.findById(anyInt())).thenReturn(new AppUser());
        Profile arg = th.createProfile(2);
        Result<Profile> actual = service.create(arg);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotCreateNullProfile() {
        when(repository.createProfile(any())).thenReturn(th.createProfile(1));
        Result<Profile> actual = service.create(null);
        assertEquals(1, actual.getMessages().size());
        assertEquals("nothing to add", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateProfileWithZeroId () {
        when(repository.createProfile(any())).thenReturn(th.createProfile(1));
        when(appUserRepository.findById(anyInt())).thenReturn(new AppUser());
        Profile arg = th.createProfile(0);
        Result<Profile> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("profile id should be set", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateProfileWithNonexistentId () {
        when(repository.createProfile(any())).thenReturn(th.createProfile(1));
        when(appUserRepository.findById(anyInt())).thenReturn(null);
        Profile arg = th.createProfile(4);
        Result<Profile> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("app user not found", actual.getMessages().get(0));
    }


    @Test
    void shouldNotCreateDuplicateProfile() {
        when(repository.createProfile(any())).thenReturn(th.createProfile(2));
        when(repository.findById(anyInt())).thenReturn(th.createProfile(2));
        when(appUserRepository.findById(anyInt())).thenReturn(new AppUser());
        Profile arg = th.createProfile(2);
        Result<Profile> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("profile already exists for this account", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateAccountWithoutFirstName() {
        when(repository.createProfile(any())).thenReturn(th.createProfile(2));
        when(appUserRepository.findById(anyInt())).thenReturn(new AppUser());
        Profile arg = th.createProfile(2);
        arg.setFirstName(null);
        Result<Profile> actual = service.create(arg);
        assertEquals(2, actual.getMessages().size());
        assertEquals("first name is required", actual.getMessages().get(0));
    }

    @Test
    void shouldUpdate() {
        when(repository.updateProfile(any())).thenReturn(true);
        when(appUserRepository.findById(anyInt())).thenReturn(new AppUser());
        Profile arg = th.createProfile(2);
        Result<Profile> actual = service.update(arg);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotUpdateWithInvalidAppUserId() {
        when(repository.updateProfile(any())).thenReturn(true);
        when(appUserRepository.findById(anyInt())).thenReturn(new AppUser());
        Profile arg = th.createProfile(0);
        Result<Profile> actual = service.update(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("profile id is required", actual.getMessages().get(0));

    }

    @Test
    void shouldNotUpdateWithNonexistentAppUser() {
        when(repository.updateProfile(any())).thenReturn(false);
        Profile arg = th.createProfile(3);
        Result<Profile> actual = service.update(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("app user not found", actual.getMessages().get(0));
    }

    @Test
    void shouldSoftDelete() {
        when(repository.softDeleteById(anyInt())).thenReturn(true);
        assertTrue(service.softDeleteById(1).isSuccess());
    }

    @Test
    void shouldNotSoftDelete() {
        when(repository.softDeleteById(anyInt())).thenReturn(false);
        assertFalse(service.softDeleteById(1).isSuccess());
    }

    @Test
    void shouldRestore() {
        when(repository.softDeleteById(anyInt())).thenReturn(true);
        assertTrue(service.softDeleteById(1).isSuccess());
    }

    @Test
    void shouldNotRestore() {
        when(repository.softDeleteById(anyInt())).thenReturn(false);
        assertFalse(service.softDeleteById(1).isSuccess());
    }


}
