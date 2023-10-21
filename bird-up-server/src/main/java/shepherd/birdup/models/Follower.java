package shepherd.birdup.models;

import shepherd.birdup.validation.DistinctFollow;
import shepherd.birdup.validation.NoFollowingSelf;
import shepherd.birdup.validation.ProfileExists;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Objects;

@DistinctFollow
@NoFollowingSelf
public class Follower {
    @ProfileExists(message="follower profile must exist")
    private Profile follower;
    @ProfileExists(message="followed profile must exist")
    private Profile followee;

    //Read only and autogenerated in sql
    private LocalDateTime createdAt;

    public Follower() {
    }

    public Follower(Profile follower, Profile followee, LocalDateTime createdAt) {
        this.follower = follower;
        this.followee = followee;
        this.createdAt = createdAt;
    }

    public Profile getFollower() {
        return follower;
    }

    public void setFollower(Profile follower) {
        this.follower = follower;
    }

    public Profile getFollowee() {
        return followee;
    }

    public void setFollowee(Profile followee) {
        this.followee = followee;
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
        Follower follower1 = (Follower) o;
        return Objects.equals(follower, follower1.follower) && Objects.equals(followee, follower1.followee) && Objects.equals(createdAt, follower1.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(follower, followee, createdAt);
    }

    @Override
    public String toString() {
        return "Follower{" +
                "follower=" + follower +
                ", followee=" + followee +
                ", createdAt=" + createdAt +
                '}';
    }
}
