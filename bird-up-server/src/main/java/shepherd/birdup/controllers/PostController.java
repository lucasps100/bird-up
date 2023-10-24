package shepherd.birdup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shepherd.birdup.domain.PostService;
import shepherd.birdup.domain.Result;
import shepherd.birdup.models.*;

import java.util.List;

@RestController
@RequestMapping("/api/birdup/post")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> findAll(@RequestParam(value = "posterId", required = false) int posterId,
                              @RequestParam(value = "stateAbbrv", required = false) String stateAbbrv,
                              @RequestParam(value = "species", required = false) String species,
                              @RequestParam(value = "postalCode", required = false) String postalCode,
                              @RequestParam(value = "likerId", required = false) int likerId,
                              @RequestParam(value = "followerId", required = false) int followerId,
                              @RequestParam(value = "city", required = false) String city

                              ) {
       if(stateAbbrv != null && city != null) {
           return service.findByCityAndStateAbbrv(city, stateAbbrv);
       }
       if(stateAbbrv != null) {
           return service.findByStateAbbrv(stateAbbrv);
       }
       if(species != null) {
           return service.findBySpeciesShortName(species);
       }
       if(postalCode != null) {
           return service.findByPostalCode(postalCode);
       }
       if(posterId > 0) {
           return service.findByAppUserId(posterId);
       }
       if(likerId > 0 ) {
           return service.findLikedPostsByLikerId(likerId);
       }
       if(followerId > 0 ) {
           return service.findFolloweePostsByFollowerId(followerId);
       }
       return service.findAll();
    }

    @GetMapping("/{postId}")
    public Post findByPostId(@PathVariable int postId) {
        return service.findByPostId(postId);
    }

//    @GetMapping
//    public List<Post> findByAppUserId(@RequestParam int appUserId) {
//        return service.findByAppUserId(appUserId);
//    }

//    @GetMapping
//    public List<Post> findByStateAbbrv(@RequestParam String stateAbbrv) {
//        return service.findByStateAbbrv(stateAbbrv);
//    }

//    @GetMapping
//    public List<Post> findBySpecies(@RequestParam String species) {
//        return service.findBySpeciesShortName(species);
//    }

//    @GetMapping
//    public List<Post> findByPostalCode(@RequestParam String postalCode) {
//        return service.findByPostalCode(postalCode);
//    }

//    @GetMapping
//    public List<Post> findLikedPosts(@RequestParam int likerId) {
//        return service.findLikedPostsByLikerId(likerId);
//    }

//    @GetMapping
//    public List<Post> findFolloweePosts(@RequestParam int followerId) {
//        return service.findFolloweePostsByFollowerId(followerId);
//    }

//    @GetMapping
//    public List<Post> findByCityAndState(@RequestParam String city, @RequestParam String stateAbbrv) {
//        return service.findByCityAndStateAbbrv(city, stateAbbrv);
//    }

    @PostMapping
    public ResponseEntity<Object> create(@AuthenticationPrincipal AppUser appUser, @RequestBody Post post) {
        Profile poster = new Profile();
        poster.setAppUserId(appUser.getId());
        post.setPosterProfile(poster);
        Result<Post> result = service.create(post);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Object> update(@AuthenticationPrincipal AppUser appUser, @PathVariable int postId, @RequestBody Post post) {
        if (appUser.getId() != post.getPosterProfile().getAppUserId() || appUser.getId() != postId) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Post> result = service.update(post);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal AppUser appUser, @PathVariable int postId) {
        if(service.findByPostId(postId).getPosterProfile().getAppUserId() != appUser.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Post> result = service.softDeleteById(postId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
