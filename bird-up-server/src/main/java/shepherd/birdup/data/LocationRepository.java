package shepherd.birdup.data;

import shepherd.birdup.models.Location;

import java.util.List;

public interface LocationRepository {
    List<Location> findAll();

    Location findById(int locationId);

    List<Location> findByStateAbbv(String stateAbbrv);

    List<Location> findByPartialName(String partialName);

    Location create(Location location);

    boolean update(Location location);

    boolean deleteById(int locationId);
}
