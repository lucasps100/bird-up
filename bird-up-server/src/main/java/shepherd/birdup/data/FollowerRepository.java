package shepherd.birdup.data;

import shepherd.birdup.models.Follower;

public interface FollowerRepository {

    Follower findByIds(int followerId, int followeeId);

    Follower create(Follower follower);

    boolean deleteByIds(int followerId, int followeeId);
}
