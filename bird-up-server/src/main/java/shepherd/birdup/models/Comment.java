package shepherd.birdup.models;

import shepherd.birdup.validation.PostExists;
import shepherd.birdup.validation.ProfileExists;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@PostExists
public class Comment implements HasPostId {
    private int commentId;
    @NotNull
    @NotBlank(message = "comment text is required")
    @Size(max = 250)
    private String commentText;
    private int postId;
    @NotNull
    @ProfileExists(message = "commenter profile must exist")
    private Profile commenterProfile;
    //read only parameter
    private LocalDateTime createdAt;

    public Comment() {
    }

    public Comment(int commentId, String commentText, int postId, Profile commenterProfile, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.commentText = commentText;
        this.postId = postId;
        this.commenterProfile = commenterProfile;
        this.createdAt = createdAt;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Profile getCommenterProfile() {
        return commenterProfile;
    }

    public void setCommenterProfile(Profile commenterProfile) {
        this.commenterProfile = commenterProfile;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return commentId == comment.commentId && postId == comment.postId && Objects.equals(commentText, comment.commentText) && Objects.equals(commenterProfile, comment.commenterProfile) && Objects.equals(createdAt, comment.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, commentText, postId, commenterProfile, createdAt);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", commentText='" + commentText + '\'' +
                ", postId=" + postId +
                ", commenterProfile=" + commenterProfile +
                ", createdAt=" + createdAt +
                '}';
    }
}
