package shepherd.birdup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shepherd.birdup.domain.FollowerService;
import shepherd.birdup.domain.Result;
import shepherd.birdup.models.AppUser;
import shepherd.birdup.models.Follower;

@RestController
@RequestMapping("/api/birdup/follower")
public class FollowerController {

    private final FollowerService service;

    public FollowerController(FollowerService service) {
        this.service = service;
    }

    @GetMapping
    public Follower findByIds(@RequestParam int followerId, @RequestParam int followeeId) {
        return service.findByIds(followerId, followeeId);
    }

    @PostMapping
    public ResponseEntity<Object> create(@AuthenticationPrincipal AppUser appUser, @RequestBody Follower follower) {
        if (follower.getFollower().getAppUserId() != appUser.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Follower> result = service.create(follower);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{followerId}/{followeeId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal AppUser appUser,
                                       @PathVariable int followerId,
                                       @PathVariable int followeeId) {
        if (followerId != appUser.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Follower> result = service.deleteByIds(followerId, followeeId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
