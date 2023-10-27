package shepherd.birdup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shepherd.birdup.domain.ProfileService;
import shepherd.birdup.domain.Result;
import shepherd.birdup.models.AppUser;
import shepherd.birdup.models.Profile;

import java.util.List;

@RestController
@RequestMapping("/api/birdup/profile")
public class ProfileController {

    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping("/{username}")
    public Profile getByUsername(@PathVariable String username) {
        return service.findByUsername(username);
    }

    @GetMapping
    public List<Profile> getByPartialName(@RequestParam String partialName) {
        return service.findByPartialName(partialName);
    }

    @PostMapping
    public ResponseEntity<Object> create(@AuthenticationPrincipal AppUser appUser, @RequestBody Profile profile) {
        profile.setAppUserId(appUser.getId());
        Result<Profile> result = service.create(profile);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping
    public ResponseEntity<Object> update(@AuthenticationPrincipal AppUser appUser, @RequestBody Profile profile) {
        profile.setAppUserId(appUser.getId());
        Result<Profile> result = service.update(profile);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping
    public ResponseEntity<Void> softDelete(@AuthenticationPrincipal AppUser appUser) {
        Result<Profile> result = service.softDeleteById(appUser.getId());
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
