package shepherd.birdup.data;

import org.springframework.transaction.annotation.Transactional;
import shepherd.birdup.models.Profile;

import java.util.List;

public interface ProfileRepository {
    @Transactional
    Profile findByUsername(String username);

    @Transactional
    Profile findById(int appUserId);

    List<Profile> findByPartialName(String partialName);

    Profile createProfile(Profile profile);

    boolean updateProfile(Profile profile);

    boolean softDeleteById(int appUserId);

    boolean restoreProfileById(int appUserId);

}
