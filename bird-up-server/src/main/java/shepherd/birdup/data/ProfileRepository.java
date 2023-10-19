package shepherd.birdup.data;

import org.springframework.transaction.annotation.Transactional;
import shepherd.birdup.models.Profile;

import java.util.List;

public interface ProfileRepository {
    @Transactional
    Profile findByUsername(String username);

    List<Profile> findByPartialName(String partialName);

    Profile createProfile(Profile profile);

    boolean updateProfile(Profile profile);

}
