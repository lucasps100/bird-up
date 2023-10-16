package shepherd.birdup.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Follower {
    private int followerid;
    private int followeeId;
    private LocalDateTime createdAt;

    public Follower() {}

    public Follower(int followerid, int followeeId, LocalDateTime createdAt) {
        this.followerid = followerid;
        this.followeeId = followeeId;
        this.createdAt = createdAt;
    }

    public int getFollowerid() {
        return followerid;
    }

    public void setFollowerid(int followerid) {
        this.followerid = followerid;
    }

    public int getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(int followeeId) {
        this.followeeId = followeeId;
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
        return followerid == follower.followerid && followeeId == follower.followeeId && Objects.equals(createdAt, follower.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerid, followeeId, createdAt);
    }

    @Override
    public String toString() {
        return "Follower{" +
                "followerid=" + followerid +
                ", followeeId=" + followeeId +
                ", createdAt=" + createdAt +
                '}';
    }
}
