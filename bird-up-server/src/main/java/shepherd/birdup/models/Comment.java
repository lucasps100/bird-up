package shepherd.birdup.models;

import java.util.Objects;

public class Comment {
    private int commentId;
    private String commentText;
    private int postId;
    private int commenterId;

    public Comment() {}

    public Comment(int commentId, String commentText, int postId, int commenterId) {
        this.commentId = commentId;
        this.commentText = commentText;
        this.postId = postId;
        this.commenterId = commenterId;
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

    public int getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(int commenterId) {
        this.commenterId = commenterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return commentId == comment.commentId && postId == comment.postId && commenterId == comment.commenterId && Objects.equals(commentText, comment.commentText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, commentText, postId, commenterId);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", commentText='" + commentText + '\'' +
                ", postId=" + postId +
                ", commenterId=" + commenterId +
                '}';
    }
}
