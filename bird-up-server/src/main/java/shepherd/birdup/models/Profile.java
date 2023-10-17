package shepherd.birdup.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Profile {

    private int appUserId;
    private String username;
    private String firstName;
    private String lastName;
    private String bio;
    private List<Post> posts;
    private List<Profile> followers;
    private List <Profile> followees;
    private List<Post> likedPosts;

    private LocalDateTime createdAt;

    public Profile() {}

    public Profile(int appUserId, String username, String firstName, String lastName, String bio, List<Post> posts, List<Profile> followers, List<Profile> followees, List<Post> likedPosts, LocalDateTime createdAt) {
        this.appUserId = appUserId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.posts = posts;
        this.followers = followers;
        this.followees = followees;
        this.likedPosts = likedPosts;
        this.createdAt = createdAt;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Profile> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Profile> followers) {
        this.followers = followers;
    }

    public List<Profile> getFollowees() {
        return followees;
    }

    public void setFollowees(List<Profile> followees) {
        this.followees = followees;
    }

    public List<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(List<Post> likedPosts) {
        this.likedPosts = likedPosts;
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
        Profile profile = (Profile) o;
        return appUserId == profile.appUserId && Objects.equals(username, profile.username) && Objects.equals(firstName, profile.firstName) && Objects.equals(lastName, profile.lastName) && Objects.equals(bio, profile.bio) && Objects.equals(posts, profile.posts) && Objects.equals(followers, profile.followers) && Objects.equals(followees, profile.followees) && Objects.equals(likedPosts, profile.likedPosts) && Objects.equals(createdAt, profile.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUserId, username, firstName, lastName, bio, posts, followers, followees, likedPosts, createdAt);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "appUserId=" + appUserId +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bio='" + bio + '\'' +
                ", posts=" + posts +
                ", followers=" + followers +
                ", followees=" + followees +
                ", likedPosts=" + likedPosts +
                ", createdAt=" + createdAt +
                '}';
    }
}
