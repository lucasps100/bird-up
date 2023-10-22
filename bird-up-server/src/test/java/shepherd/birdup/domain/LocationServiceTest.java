package shepherd.birdup.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shepherd.birdup.TestHelper;
import shepherd.birdup.data.LocationRepository;
import shepherd.birdup.data.StateRepository;
import shepherd.birdup.models.Location;
import shepherd.birdup.models.State;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LocationServiceTest {

    @MockBean
    LocationRepository locationRepository;

    @MockBean
    StateRepository stateRepository;

    @Autowired
    LocationService service;

    TestHelper th = new TestHelper();

    @Test
    void shouldFindAll() {
        when(locationRepository.findAll()).thenReturn(List.of(th.createLocation(1)));
        assertEquals(service.findAll(), List.of(th.createLocation(1)));
    }

    @Test
    void shouldFindById() {
        when(locationRepository.findById(anyInt())).thenReturn(th.createLocation(1));
        assertEquals(service.findById(1), th.createLocation(1));
    }

    @Test
    void shouldFindByStateAbbrv() {
        when(locationRepository.findByStateAbbrv(any())).thenReturn(List.of(th.createLocation(1)));
        assertEquals(service.findByStateAbbrv("ME"), List.of(th.createLocation(1)));
    }

    @Test
    void shouldCreate(){
        when(locationRepository.create(any())).thenReturn(th.createLocation(1));
        when(stateRepository.findStateById(anyInt())).thenReturn(new State(12, "Maine", "ME"));
        Location arg = th.createLocation(0);
        Result<Location> actual = service.create(arg);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotCreateNullLocation(){
        Result<Location> actual = service.create(null);
        assertEquals(1, actual.getMessages().size());
        assertEquals("nothing to add", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateLocationWithPresetId() {
        when(locationRepository.create(any())).thenReturn(th.createLocation(1));
        when(stateRepository.findStateById(anyInt())).thenReturn(new State(12, "Maine", "ME"));
        Location arg = th.createLocation(1);
        Result<Location> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("location id should not be set", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateLocationWithNonNumericPostalCode() {
        when(locationRepository.create(any())).thenReturn(th.createLocation(1));
        when(stateRepository.findStateById(anyInt())).thenReturn(new State(12, "Maine", "ME"));
        Location arg = th.createLocation(0);
        arg.setPostalCode("a1111");
        Result<Location> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("invalid postal code", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddLocationWithTooLongPostalCode() {
        when(locationRepository.create(any())).thenReturn(th.createLocation(1));
        when(stateRepository.findStateById(anyInt())).thenReturn(new State(12, "Maine", "ME"));
        Location arg = th.createLocation(0);
        arg.setPostalCode("111111");
        Result<Location> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("postal code must be 5 digits", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddLocationWithInvalidState() {
        when(locationRepository.create(any())).thenReturn(th.createLocation(1));
        Location arg = th.createLocation(0);
        Result<Location> actual = service.create(arg);
        assertEquals(1, actual.getMessages().size());
        assertEquals("state must exist", actual.getMessages().get(0));
    }
    // TODO: Test update for location service

    @Test
    void shouldDelete() {
        when(locationRepository.deleteById(anyInt())).thenReturn(true);
        assertTrue(service.deleteByLocationId(1).isSuccess());
    }

    @Test
    void shouldNotDelete() {
        when(locationRepository.deleteById(anyInt())).thenReturn(false);
        Result<Location> actual = service.deleteByLocationId(1);
        assertEquals(List.of("location 1 not found"), actual.getMessages());
    }
 }
