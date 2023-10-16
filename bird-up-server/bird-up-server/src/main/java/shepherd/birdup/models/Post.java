package shepherd.birdup.models;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Post {
    private int posterId;
    private int postId;
    private Blob image;
    private Location postLocation;
    private LocalDateTime createdAt;
    private String species;
    private List<Like> likes;
    private List<Comment> comments;

    private String postText;

    public Post() {}

    public Post(int posterId, int postId, Blob image, Location postLocation, LocalDateTime createdAt, String species, List<Like> likes, List<Comment> comments, String postText) {
        this.posterId = posterId;
        this.postId = postId;
        this.image = image;
        this.postLocation = postLocation;
        this.createdAt = createdAt;
        this.species = species;
        this.likes = likes;
        this.comments = comments;
        this.postText = postText;
    }

    public int getPosterId() {
        return posterId;
    }

    public void setPosterId(int posterId) {
        this.posterId = posterId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public Location getPostLocation() {
        return postLocation;
    }

    public void setPostLocation(Location postLocation) {
        this.postLocation = postLocation;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return posterId == post.posterId && postId == post.postId && Objects.equals(image, post.image) && Objects.equals(postLocation, post.postLocation) && Objects.equals(createdAt, post.createdAt) && Objects.equals(species, post.species) && Objects.equals(likes, post.likes) && Objects.equals(comments, post.comments) && Objects.equals(postText, post.postText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posterId, postId, image, postLocation, createdAt, species, likes, comments, postText);
    }

    @Override
    public String toString() {
        return "Post{" +
                "posterId=" + posterId +
                ", postId=" + postId +
                ", image=" + image +
                ", postLocation=" + postLocation +
                ", createdAt=" + createdAt +
                ", species='" + species + '\'' +
                ", likes=" + likes +
                ", comments=" + comments +
                ", postText='" + postText + '\'' +
                '}';
    }
}
