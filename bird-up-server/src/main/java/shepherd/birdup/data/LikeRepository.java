package shepherd.birdup.data;

import shepherd.birdup.models.Like;

import java.util.List;

public interface LikeRepository {
    List<Like> findByPostId(int postId);

    Like create(Like like);

    boolean deleteByIds(int appUserId, int postId);
}
