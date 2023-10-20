package shepherd.birdup.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shepherd.birdup.data.mappers.ProfileMapper;
import shepherd.birdup.models.Profile;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ProfileJdbcTemplateRepository implements ProfileRepository {

    private final JdbcTemplate jdbcTemplate;

    private final PostRepository postJdbcTemplateRepository;

    public ProfileJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.postJdbcTemplateRepository = new PostJdbcTemplateRepository(jdbcTemplate);
    }

    @Override
    @Transactional
    public Profile findByUsername(String username) {
        final String sql = """
                select a.app_user_id, username, first_name, last_name, bio, created_at
                from profile p
                join app_user a
                on p.app_user_id = a.app_user_id
                where username like ?
                and a.enabled = true;
                """;
        Profile profile = jdbcTemplate.query(sql, new ProfileMapper(), username).stream().findFirst().orElse(null);
        if(profile != null) {
            profile.setPosts(postJdbcTemplateRepository.findByAppUserId(profile.getAppUserId()));
            addFollowees(profile);
            addFollowees(profile);
        }
        return profile;

    }

    @Override
    public Profile findById(int appUserId) {
        final String sql = """
                select a.app_user_id, username, first_name, last_name, bio, created_at
                from profile p
                join app_user a
                on p.app_user_id = a.app_user_id
                where p.app_user_id = ?
                and a.enabled = true;
                """;
        Profile profile = jdbcTemplate.query(sql, new ProfileMapper(), appUserId).stream().findFirst().orElse(null);
        if(profile != null) {
            profile.setPosts(postJdbcTemplateRepository.findByAppUserId(profile.getAppUserId()));
            addFollowees(profile);
            addFollowees(profile);
        }
        return profile;
    }

    private void addFollowers(Profile profile) {
        final String sql = """
                select a.app_user_id, username, first_name, last_name, bio, created_at
                from profile p
                join app_user a
                on p.app_user_id = a.app_user_id
                join follower f
                on a.app_user_id = f.follower_id
                where f.followee_id = ?
                and a.enabled = true;
                """;
        List<Profile> followers = jdbcTemplate.query(sql, new ProfileMapper(), profile.getAppUserId());
        profile.setFollowers(followers);
    }

    private void addFollowees(Profile profile) {
        final String sql = """
                select a.app_user_id, username, first_name, last_name, bio, p.created_at
                from profile p
                join app_user a
                on p.app_user_id = a.app_user_id
                join follower f
                on a.app_user_id = f.followee_id
                where f.follower_id = ?
                and a.enabled = true;
                """;
        List<Profile> followees = jdbcTemplate.query(sql, new ProfileMapper(), profile.getAppUserId());
        profile.setFollowees(followees);
    }

    @Override
    public List<Profile> findByPartialName(String partialName) {
        final String sql = """
                select a.app_user_id, username, first_name, last_name, bio, created_at
                from profile p
                join app_user a
                on p.app_user_id = a.app_user_id
                where (username like ?
                or first_name like ?
                or last_name like ?)
                and a.enabled = true;
                """;
        return jdbcTemplate.query(sql, new ProfileMapper(), "%"+partialName+"%", "%"+partialName+"%", "%"+partialName+"%");
    }

    @Override
    public Profile createProfile(Profile profile) {
        final String sql = """
            insert into profile (app_user_id, first_name, last_name, bio)
            value (?, ?, ?, ?);
            """;
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, profile.getAppUserId());
            ps.setString(2, profile.getFirstName());
            ps.setString(3, profile.getLastName());
            ps.setString(4, profile.getBio());
            return ps;
        });

        if (rowsAffected <= 0) {
            return null;
        }
        return profile;
    }

    @Override
    public boolean updateProfile(Profile profile) {
        final String sql = """
                update profile set
                first_name = ?,
                last_name = ?,
                bio = ?
                where app_user_id = ?
                """;
        return jdbcTemplate.update(sql, profile.getFirstName(),
                profile.getLastName(), profile.getBio(), profile.getAppUserId()) > 0;
    }
}
