package shepherd.birdup.models;

import shepherd.birdup.validation.LocationExists;
import shepherd.birdup.validation.ProfileExists;
import shepherd.birdup.validation.SpeciesExists;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Post implements HasPostId {
    @ProfileExists(message = "poster profile must exist")
    private Profile posterProfile;
    private int postId;
    //@NotNull(message = "an image is required")
    private Blob image;
//    @NotNull
//    @LocationExists
    private Location postLocation;
    //read only
    private LocalDateTime createdAt;
    @NotNull(message = "a species label is required")
    @SpeciesExists
    private Species species;

    private List<Like> likes;

    private List<Comment> comments;
    @Size(max = 255)
    private String postText;

    public Post() {
    }

    public Post(Profile posterProfile, int postId, Blob image, Location postLocation,
                LocalDateTime createdAt, Species species, List<Like> likes, List<Comment> comments, String postText) {
        this.posterProfile = posterProfile;
        this.postId = postId;
        this.image = image;
        this.postLocation = postLocation;
        this.createdAt = createdAt;
        this.species = species;
        this.likes = likes;
        this.comments = comments;
        this.postText = postText;
    }

    public Profile getPosterProfile() {
        return posterProfile;
    }

    public void setPosterProfile(Profile posterProfile) {
        this.posterProfile = posterProfile;
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

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
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
        return postId == post.postId && Objects.equals(posterProfile, post.posterProfile) && Objects.equals(image, post.image) && Objects.equals(postLocation, post.postLocation) && Objects.equals(createdAt, post.createdAt) && Objects.equals(species, post.species) && Objects.equals(likes, post.likes) && Objects.equals(comments, post.comments) && Objects.equals(postText, post.postText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posterProfile, postId, image, postLocation, createdAt, species, likes, comments, postText);
    }

    @Override
    public String toString() {
        return "Post{" +
                "posterProfile=" + posterProfile +
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
