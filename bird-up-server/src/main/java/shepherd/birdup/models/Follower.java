package shepherd.birdup.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Follower {
    private Profile followerProfile;
    private Profile followeeProfile;
    private LocalDateTime createdAt;

    public Follower() {}

    public Follower(Profile followerProfile, Profile followeeProfile, LocalDateTime createdAt) {
        this.followerProfile = followerProfile;
        this.followeeProfile = followeeProfile;
        this.createdAt = createdAt;
    }

    public Profile getFollowerProfile() {
        return followerProfile;
    }

    public void setFollowerProfile(Profile followerProfile) {
        this.followerProfile = followerProfile;
    }

    public Profile getFolloweeProfile() {
        return followeeProfile;
    }

    public void setFolloweeProfile(Profile followeeProfile) {
        this.followeeProfile = followeeProfile;
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
        Follower follower = (Follower) o;
        return Objects.equals(followerProfile, follower.followerProfile) && Objects.equals(followeeProfile, follower.followeeProfile) && Objects.equals(createdAt, follower.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerProfile, followeeProfile, createdAt);
    }

    @Override
    public String toString() {
        return "Follower{" +
                "followerProfile=" + followerProfile +
                ", followeeProfile=" + followeeProfile +
                ", createdAt=" + createdAt +
                '}';
    }
}
