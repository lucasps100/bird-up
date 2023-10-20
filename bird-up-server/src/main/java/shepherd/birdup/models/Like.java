package shepherd.birdup.models;

import shepherd.birdup.validation.DistinctLike;
import shepherd.birdup.validation.PostExists;
import shepherd.birdup.validation.ProfileExists;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@DistinctLike
@PostExists
public class Like implements HasPostId{
    @NotNull
    @ProfileExists(message = "liker profile must exist")
    private Profile likerAccount;
    private int postId;

    public Like() {}

    public Like(Profile likerAccount, int postId) {
        this.likerAccount = likerAccount;
        this.postId = postId;
    }

    public Profile getLikerAccount() {
        return likerAccount;
    }

    public void setLikerAccount(Profile likerAccount) {
        this.likerAccount = likerAccount;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return postId == like.postId && Objects.equals(likerAccount, like.likerAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(likerAccount, postId);
    }

    @Override
    public String toString() {
        return "Like{" +
                "likerAccount=" + likerAccount +
                ", postId=" + postId +
                '}';
    }
}
