package shepherd.birdup.models;

import java.util.Objects;

public class Like {
    private int likerId;
    private int postId;

    public Like() {}

    public Like(int likerId, int postId) {
        this.likerId = likerId;
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return likerId == like.likerId && postId == like.postId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(likerId, postId);
    }

    @Override
    public String toString() {
        return "Like{" +
                "likerId=" + likerId +
                ", postId=" + postId +
                '}';
    }
}
