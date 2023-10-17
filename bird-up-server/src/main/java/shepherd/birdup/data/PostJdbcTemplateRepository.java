package shepherd.birdup.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shepherd.birdup.data.mappers.PostMapper;
import shepherd.birdup.models.Post;

import java.util.List;

@Repository
public class PostJdbcTemplateRepository {

    private final JdbcTemplate jdbcTemplate;

    private final LikeJdbcTemplateRepository likeRepository;
    private final CommentJdbcTemplateRepository commentRepository;

    public PostJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.likeRepository = new LikeJdbcTemplateRepository(jdbcTemplate);
        this.commentRepository = new CommentJdbcTemplateRepository(jdbcTemplate);
    }

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
                on po.location_id = location.location_id
                join species sp
                on sp.species_id = po.species_id,
                join state st
                on st.state_id = l.state_id
                where a.app_user_id
                order by po.created_at DESC;
                """;
        List<Post> posts = jdbcTemplate.query(sql, new PostMapper(), appUserId);
        posts.forEach(p -> {
                    p.setComments(commentRepository.findByPostId(p.getPostId()));
                    p.setLikes(likeRepository.findByPostId(p.getPostId()));
                });
        return posts;
    }

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
                on po.location_id = location.location_id
                join species sp
                on sp.species_id = po.species_id,
                join state st
                on st.state_id = l.state_id
                where st.state_abbrv = ?
                order by po.created_at DESC;
                """;
        List<Post> posts = jdbcTemplate.query(sql, new PostMapper(), stateAbbrv);
        posts.forEach(p -> {
            p.setComments(commentRepository.findByPostId(p.getPostId()));
            p.setLikes(likeRepository.findByPostId(p.getPostId()));
        });
        return posts;
    }

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
                on po.location_id = location.location_id
                join species sp
                on sp.species_id = po.species_id,
                join state st
                on st.state_id = l.state_id
                where sp.species_short_name = ?
                order by po.created_at DESC;
                """;
        List<Post> posts = jdbcTemplate.query(sql, new PostMapper(), speciesShortName);
        posts.forEach(p -> {
            p.setComments(commentRepository.findByPostId(p.getPostId()));
            p.setLikes(likeRepository.findByPostId(p.getPostId()));
        });
        return posts;
    }

    public List<Post> findByPostalCode(int postalCode) {
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
                on po.location_id = location.location_id
                join species sp
                on sp.species_id = po.species_id,
                join state st
                on st.state_id = l.state_id
                where l.postal_code = ?
                order by po.created_at DESC;
                """;
        List<Post> posts = jdbcTemplate.query(sql, new PostMapper(), postalCode);
        posts.forEach(p -> {
            p.setComments(commentRepository.findByPostId(p.getPostId()));
            p.setLikes(likeRepository.findByPostId(p.getPostId()));
        });
        return posts;
    }

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
                on po.location_id = location.location_id
                join species sp
                on sp.species_id = po.species_id,
                join state st
                on st.state_id = l.state_id
                where l.city = ? AND st.state_abbrv = ?
                order by po.created_at DESC;
                """;
        List<Post> posts = jdbcTemplate.query(sql, new PostMapper(), city, stateAbbrv);
        posts.forEach(p -> {
            p.setComments(commentRepository.findByPostId(p.getPostId()));
            p.setLikes(likeRepository.findByPostId(p.getPostId()));
        });
        return posts;
    }

    public Post create(Post post) {

    }








}
