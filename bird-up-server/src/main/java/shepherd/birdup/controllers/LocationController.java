package shepherd.birdup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shepherd.birdup.domain.LocationService;
import shepherd.birdup.domain.Result;
import shepherd.birdup.models.AppUser;
import shepherd.birdup.models.Follower;
import shepherd.birdup.models.Location;
import shepherd.birdup.models.Profile;

import java.util.List;

@RestController
@RequestMapping("/api/birdup/location")
public class LocationController {
    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Location> findList(@RequestParam(value = "partialName", required = false) String partialName,
                                   @RequestParam(value = "stateAbbrv", required = false) String stateAbbrv) {
        if(partialName == null && stateAbbrv == null) {
            return service.findAll();
        }
        if(stateAbbrv == null) {
            return service.findByPartialName(partialName);
        }
        return service.findByStateAbbrv(stateAbbrv);
    }

    @GetMapping("/{locationId}")
    public Location findById(@PathVariable int locationId) {
        return service.findById(locationId);
    }

//    @GetMapping("/search")
//    public List<Location> findByStateAbbrv(@RequestParam(value = "stateAbbrv", required = false) String stateAbbrv) {
//        return service.findByStateAbbrv(stateAbbrv);
//    }
//
//    @GetMapping("/search")
//    public List<Location> findByPartialName(@RequestParam(value = "partialName", required = false) String partialName) {
//        return service.findByPartialName(partialName);
//    }

    @PostMapping
    public ResponseEntity<Object> create(@AuthenticationPrincipal AppUser appUser, @RequestBody Location location) {
        Result<Location> result = service.create(location);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<Object> update(@AuthenticationPrincipal AppUser appUser, @PathVariable int locationId, @RequestBody Location location) {
        if (locationId != location.getLocationId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Location> result = service.update(location);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal AppUser appUser, @PathVariable int locationId) {
        Result<Location> result = service.deleteByLocationId(locationId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
