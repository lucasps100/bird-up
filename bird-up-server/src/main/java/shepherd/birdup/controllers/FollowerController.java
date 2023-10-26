package shepherd.birdup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shepherd.birdup.domain.FollowerService;
import shepherd.birdup.domain.Result;
import shepherd.birdup.models.AppUser;
import shepherd.birdup.models.Follower;
import shepherd.birdup.models.Profile;

@RestController
@RequestMapping("/api/birdup/follower")
public class FollowerController {

    private final FollowerService service;

    public FollowerController(FollowerService service) {
        this.service = service;
    }

    @GetMapping("/{followeeId}")
    public Follower findByIds(@AuthenticationPrincipal AppUser appUser, @PathVariable int followeeId) {
        return service.findByIds(appUser.getId(), followeeId);
    }

    @PostMapping("/{followeeId}")
    public ResponseEntity<Object> create(@AuthenticationPrincipal AppUser appUser, @PathVariable int followeeId, @RequestBody Follower follower) {
        Profile followerProfile = new Profile();
        followerProfile.setAppUserId(appUser.getId());
        follower.setFollower(followerProfile);
        Profile followeeProfile = new Profile();
        followeeProfile.setAppUserId(followeeId);
        follower.setFollowee(followeeProfile);
        Result<Follower> result = service.create(follower);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{followeeId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal AppUser appUser,
                                       @PathVariable int followeeId) {

        Result<Follower> result = service.deleteByIds(appUser.getId(), followeeId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
