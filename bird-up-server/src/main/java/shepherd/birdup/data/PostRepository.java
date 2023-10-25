package shepherd.birdup.data;

import org.springframework.transaction.annotation.Transactional;
import shepherd.birdup.models.Post;

import java.util.List;

public interface PostRepository {

    @Transactional
    List<Post> findAll();

    @Transactional
    Post findByPostId(int postId);

    @Transactional
    List<Post> findByAppUserId(int appUserId);

//    @Transactional
//    List<Post> findByStateAbbrv(String stateAbbrv);

    @Transactional
    List<Post> findBySpeciesShortName(String speciesShortName);

//    List<Post> findByPostalCode(String postalCode);

    @Transactional
    List<Post> findLikedPostsByLikerId(int likerId);

    @Transactional
    List<Post> findFolloweePostsByFollowerId(int followerId);

//    @Transactional
//    List<Post> findByCityAndStateAbbrv(String city, String stateAbbrv);

    Post create(Post post);

    boolean update(Post post);

    boolean softDeleteById(int postId);
}
