package shepherd.birdup.models;

import java.util.List;
import java.util.Objects;

public class Profile {

    private int appUserId;
    private String userName;
    private List<Post> posts;
    private List<Profile> followers;
    private List <Profile> followees;
    private List<Post> likedPosts;

    public Profile(int appUserId, String userName,
                   List<Post> posts, List<Post> likedPosts,
                   List<Profile> followers, List<Profile> followees) {
        this.appUserId = appUserId;
        this.userName = userName;
        this.posts = posts;
        this.likedPosts = likedPosts;
        this.followees = followees;
        this.followers = followers;
    }

    public Profile(){}

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getUserName() {
        return userName;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(List<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return appUserId == profile.appUserId && Objects.equals(userName, profile.userName) && Objects.equals(posts, profile.posts) && Objects.equals(followers, profile.followers) && Objects.equals(followees, profile.followees) && Objects.equals(likedPosts, profile.likedPosts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUserId, userName, posts, followers, followees, likedPosts);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "appUserId=" + appUserId +
                ", userName='" + userName + '\'' +
                ", posts=" + posts +
                ", followers=" + followers +
                ", followees=" + followees +
                ", likedPosts=" + likedPosts +
                '}';
    }
}
