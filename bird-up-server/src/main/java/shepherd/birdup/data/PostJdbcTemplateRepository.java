package shepherd.birdup.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shepherd.birdup.data.mappers.PostMapper;
import shepherd.birdup.models.Post;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class PostJdbcTemplateRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    public PostJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.likeRepository = new LikeJdbcTemplateRepository(jdbcTemplate);
        this.commentRepository = new CommentJdbcTemplateRepository(jdbcTemplate);
    }


    @Override
    public List<Post> findAll() {
        final String sql = """
                select po.post_id, po.post_body, po.image, po.created_at,
                sp.species_id, species_short_name, species_long_name, a.app_user_id,
                a.username, first_name, last_name, bio, l.state_id, state_abbrv, state_name,
                l.location_id, city, postal_code
                from post po
                join profile p
                on po.app_poster_id = p.app_user_id
                join app_user a
                on a.app_user_id = p.app_user_id
                join location l
                on po.location_id = l.location_id
                join species sp
                on sp.species_id = po.species_id
                join state st
                on st.state_id = l.state_id
                where po.enabled = true
                and p.enabled = true
                order by po.created_at DESC;
                """;

        List<Post> posts = jdbcTemplate.query(sql, new PostMapper());
        posts.forEach(p -> {
            p.setComments(commentRepository.findByPostId(p.getPostId()));
            p.setLikes(likeRepository.findByPostId(p.getPostId()));
        });
        return posts;
    }


    @Transactional
    @Override
    public Post findByPostId(int postId) {
        final String sql = """
                select po.post_id, po.post_body, po.image, po.created_at,
                sp.species_id, species_short_name, species_long_name, a.app_user_id,
                a.username, first_name, last_name, bio, l.state_id, state_abbrv, state_name,
                l.location_id, city, postal_code
                from post po
                join profile p
                on po.app_poster_id = p.app_user_id
                join app_user a
                on a.app_user_id = p.app_user_id
                join location l
                on po.location_id = l.location_id
                join species sp
                on sp.species_id = po.species_id
                join state st
                on st.state_id = l.state_id
                where po.enabled = true
                and p.enabled = true
                and po.post_id = ?
                order by po.created_at DESC;
                """;

        Post post = jdbcTemplate.query(sql, new PostMapper(), postId).stream().findFirst().orElse(null);
        if(post != null) {
            post.setComments(commentRepository.findByPostId(post.getPostId()));
            post.setLikes(likeRepository.findByPostId(post.getPostId()));
        }
        return post;
    }

    @Override
    @Transactional
    public List<Post> findByAppUserId(int appUserId) {
        final String sql = """
                select po.post_id, po.post_body, po.image, po.created_at,
                sp.species_id, species_short_name, species_long_name, a.app_user_id,
                a.username, first_name, last_name, bio, l.state_id, state_abbrv, state_name,
                l.location_id, city, postal_code
                from post po
                join profile p
                on po.app_poster_id = p.app_user_id
                join app_user a
                on a.app_user_id = p.app_user_id
                join location l
                on po.location_id = l.location_id
                join species sp
                on sp.species_id = po.species_id
                join state st
                on st.state_id = l.state_id
                where a.app_user_id = ?
                and po.enabled = true
                and p.enabled = true
                order by po.created_at DESC;
                """;
        List<Post> posts = jdbcTemplate.query(sql, new PostMapper(), appUserId);
        posts.forEach(p -> {
                    p.setComments(commentRepository.findByPostId(p.getPostId()));
                    p.setLikes(likeRepository.findByPostId(p.getPostId()));
                });
        return posts;
    }

    @Override
    @Transactional
    public List<Post> findByStateAbbrv(String stateAbbrv) {
        final String sql = """
                select po.post_id, po.post_body, po.image, po.created_at,
                sp.species_id, species_short_name, species_long_name, a.app_user_id,
                a.username, first_name, last_name, bio, l.state_id, state_abbrv, state_name,
                l.location_id, city, postal_code
                from post po
                join profile p
                on po.app_poster_id = p.app_user_id
                join app_user a
                on a.app_user_id = p.app_user_id
                join location l
                on po.location_id = l.location_id
                join species sp
                on sp.species_id = po.species_id
                join state st
                on st.state_id = l.state_id
                where st.state_abbrv like ?
                AND po.enabled = true
                and p.enabled = true
                order by po.created_at DESC;
                """;
        List<Post> posts = jdbcTemplate.query(sql, new PostMapper(), stateAbbrv);
        posts.forEach(p -> {
            p.setComments(commentRepository.findByPostId(p.getPostId()));
            p.setLikes(likeRepository.findByPostId(p.getPostId()));
        });
        return posts;
    }

    @Override
    @Transactional
    public List<Post> findBySpeciesShortName(String speciesShortName) {
        final String sql = """
                select po.post_id, po.post_body, po.image, po.created_at,
                sp.species_id, species_short_name, species_long_name, a.app_user_id,
                a.username, first_name, last_name, bio, l.state_id, state_abbrv, state_name,
                l.location_id, city, postal_code
                from post po
                join profile p
                on po.app_poster_id = p.app_user_id
                join app_user a
                on a.app_user_id = p.app_user_id
                join location l
                on po.location_id = l.location_id
                join species sp
                on sp.species_id = po.species_id
                join state st
                on st.state_id = l.state_id
                where sp.species_short_name like ?
                AND po.enabled = true
                and p.enabled = true
                order by po.created_at DESC;
                """;
        List<Post> posts = jdbcTemplate.query(sql, new PostMapper(), speciesShortName);
        posts.forEach(p -> {
            p.setComments(commentRepository.findByPostId(p.getPostId()));
            p.setLikes(likeRepository.findByPostId(p.getPostId()));
        });
        return posts;
    }

    @Override
    public List<Post> findByPostalCode(String postalCode) {
        final String sql = """
                select po.post_id, po.post_body, po.image, po.created_at,
                sp.species_id, species_short_name, species_long_name, a.app_user_id,
                a.username, first_name, last_name, bio, l.state_id, state_abbrv, state_name,
                l.location_id, city, postal_code
                from post po
                join profile p
                on po.app_poster_id = p.app_user_id
                join app_user a
                on a.app_user_id = p.app_user_id
                join location l
                on po.location_id = l.location_id
                join species sp
                on sp.species_id = po.species_id
                join state st
                on st.state_id = l.state_id
                where l.postal_code like ?
                AND po.enabled = true
                and p.enabled = true
                order by po.created_at DESC;
                """;
        List<Post> posts = jdbcTemplate.query(sql, new PostMapper(), postalCode);
        posts.forEach(p -> {
            p.setComments(commentRepository.findByPostId(p.getPostId()));
            p.setLikes(likeRepository.findByPostId(p.getPostId()));
        });
        return posts;
    }
    @Override
    @Transactional
    public List<Post> findLikedPostsByLikerId(int likerId) {
        final String sql = """
                select po.post_id, po.post_body, po.image, po.created_at,
                sp.species_id, species_short_name, species_long_name, a.app_user_id,
                a.username, first_name, last_name, bio, l.state_id, state_abbrv, state_name,
                l.location_id, city, postal_code
                from post po
                join profile p
                on po.app_poster_id = p.app_user_id
                join app_user a
                on a.app_user_id = p.app_user_id
                join location l
                on po.location_id = l.location_id
                join species sp
                on sp.species_id = po.species_id
                join state st
                on st.state_id = l.state_id
                join post_like
                on po.post_id = post_like.post_id
                where post_like.user_liker_id = ?
                AND po.enabled = true
                and p.enabled = true
                order by po.created_at DESC;
                """;
        List<Post> posts = jdbcTemplate.query(sql, new PostMapper(), likerId);
        posts.forEach(p -> {
            p.setComments(commentRepository.findByPostId(p.getPostId()));
            p.setLikes(likeRepository.findByPostId(p.getPostId()));
        });
        return posts;
    }

    @Override
    @Transactional
    public List<Post> findFolloweePostsByFollowerId(int followerId) {
        final String sql = """
                select po.post_id, po.post_body, po.image, po.created_at,
                sp.species_id, species_short_name, species_long_name, a.app_user_id,
                a.username, first_name, last_name, bio, l.state_id, state_abbrv, state_name,
                l.location_id, city, postal_code
                from post po
                join profile p
                on po.app_poster_id = p.app_user_id
                join app_user a
                on a.app_user_id = p.app_user_id
                join location l
                on po.location_id = l.location_id
                join species sp
                on sp.species_id = po.species_id
                join state st
                on st.state_id = l.state_id
                join follower f
                on f.followee_id = a.app_user_id
                where f.follower_id = ?
                AND po.enabled = true
                and p.enabled = true
                order by po.created_at DESC;
                """;
        List<Post> posts = jdbcTemplate.query(sql, new PostMapper(), followerId);
        posts.forEach(p -> {
            p.setComments(commentRepository.findByPostId(p.getPostId()));
            p.setLikes(likeRepository.findByPostId(p.getPostId()));
        });
        return posts;
    }

    @Override
    @Transactional
    public List<Post> findByCityAndStateAbbrv(String city, String stateAbbrv) {
        final String sql = """
                select po.post_id, po.post_body, po.image, po.created_at,
                sp.species_id, species_short_name, species_long_name, a.app_user_id,
                a.username, first_name, last_name, bio, l.state_id, state_abbrv, state_name,
                l.location_id, city, postal_code
                from post po
                join profile p
                on po.app_poster_id = p.app_user_id
                join app_user a
                on a.app_user_id = p.app_user_id
                join location l
                on po.location_id = l.location_id
                join species sp
                on sp.species_id = po.species_id
                join state st
                on st.state_id = l.state_id
                where l.city like ? AND st.state_abbrv like ?
                AND po.enabled = true
                and p.enabled = true
                order by po.created_at DESC;
                """;
        List<Post> posts = jdbcTemplate.query(sql, new PostMapper(), city, stateAbbrv);
        posts.forEach(p -> {
            p.setComments(commentRepository.findByPostId(p.getPostId()));
            p.setLikes(likeRepository.findByPostId(p.getPostId()));
        });
        return posts;
    }

    @Override
    public Post create(Post post) {

        final String sql = """
            insert into post (location_id, app_poster_id, post_body, image, species_id)
            value (?, ?, ?, ?, ?);
            """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, post.getPostLocation().getLocationId());
            ps.setInt(2, post.getPosterProfile().getAppUserId());
            ps.setString(3, post.getPostText());
            ps.setBlob(4, post.getImage());
            ps.setInt(5, post.getSpecies().getSpeciesId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        post.setPostId(keyHolder.getKey().intValue());
        return post;
    }

    @Override
    public boolean update(Post post) {
        final String sql = """
                update post set
                location_id = ?,
                app_poster_id = ?,
                post_body = ?,
                image = ?,
                species_id = ?
                where post_id = ?;
                """;

        return jdbcTemplate.update(sql, post.getPostLocation().getLocationId(),
                post.getPosterProfile().getAppUserId(),
                post.getPostText(),
                post.getImage(),
                post.getSpecies().getSpeciesId(),
                post.getPostId()) > 0;
    }

    @Override
    public boolean softDeleteById(int postId) {
        final String sql = """
                update post set
                enabled = false
                where post_id = ?;
                """;
        return jdbcTemplate.update(sql, postId) > 0;
    }

}
