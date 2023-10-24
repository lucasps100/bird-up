package shepherd.birdup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shepherd.birdup.domain.CommentService;
import shepherd.birdup.domain.Result;
import shepherd.birdup.models.AppUser;
import shepherd.birdup.models.Comment;
import shepherd.birdup.models.Profile;

@RestController
@RequestMapping("/api/birdup/comment")
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping("/{commentId}")
    public Comment findById(@PathVariable int commentId) {
        return service.findById(commentId);
    }

    @PostMapping
    public ResponseEntity<Object> create(@AuthenticationPrincipal AppUser appUser, @RequestBody Comment comment) {
        Profile commenter = new Profile();
        commenter.setAppUserId(appUser.getId());
        comment.setCommenterProfile(commenter);
        Result<Comment> result = service.create(comment);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Object> update(@AuthenticationPrincipal AppUser appUser, @PathVariable int commentId, @RequestBody Comment comment) {
        if (commentId != comment.getCommentId() || service.findById(commentId) == null || service.findById(commentId).getCommenterProfile().getAppUserId() != appUser.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Profile commenter = new Profile();
        commenter.setAppUserId(appUser.getId());
        comment.setCommenterProfile(commenter);
        comment.setPostId(service.findById(commentId).getPostId());
        Result<Comment> result = service.update(comment);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal AppUser appUser, @PathVariable int commentId) {
        if (service.findById(commentId).getCommenterProfile().getAppUserId() != appUser.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Comment> result = service.deleteByCommentId(commentId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
