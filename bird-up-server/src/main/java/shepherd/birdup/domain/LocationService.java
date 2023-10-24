package shepherd.birdup.domain;

import org.springframework.stereotype.Service;
import shepherd.birdup.data.LocationRepository;
import shepherd.birdup.models.Location;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class LocationService {

    private final LocationRepository repository;

    private final Validator validator;


    public LocationService(LocationRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public List<Location> findAll() {
        return repository.findAll();
    }

    public Location findById(int locationId) {
        return repository.findById(locationId);
    }

    public List<Location> findByStateAbbrv(String stateAbbrv) {
        return repository.findByStateAbbrv(stateAbbrv);
    }

    public List<Location> findByPartialName(String partialName) {
        return repository.findByPartialName(partialName);
    }

    public Result<Location> create(Location location) {
        Result<Location> result = new Result<>();
        if (location == null) {
            result.addMessage(ResultType.INVALID, "nothing to add");
            return result;
        }
        if (location.getLocationId() > 0) {
            result.addMessage(ResultType.INVALID, "location id should not be set");
            return result;
        }
        result = validateLocation(location);
        if (result.isSuccess()) {
            location = repository.create(location);
            result.setPayload(location);
        }
        return result;
    }

    public Result<Location> update(Location location) {
        Result<Location> result = new Result<>();
        if (location == null) {
            result.addMessage(ResultType.INVALID, "nothing to update");
            return result;
        }
        result = validateLocation(location);
        if (!result.isSuccess()) {
            return result;
        }

        if (location.getLocationId() < 1) {
            result.addMessage(ResultType.INVALID, "location id is required");
            return result;
        }
        if (!repository.update(location)) {
            result.addMessage(ResultType.NOT_FOUND, String.format("location %s not found", location.getLocationId()));
        }
        return result;
    }

    public Result<Location> deleteByLocationId(int locationId) {
        Result<Location> result = new Result<>();
        if (!repository.deleteById(locationId)) {
            String msg = String.format("location %s not found", locationId);
            result.addMessage(ResultType.NOT_FOUND, msg);
        }
        return result;
    }

    private Result<Location> validateLocation(Location location) {
        Result<Location> result = new Result<>();

        Set<ConstraintViolation<Object>> violations = validator.validate(location);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<?> violation : violations) {
                result.addMessage(ResultType.INVALID, violation.getMessage());
            }
        }
        return result;
    }
}
