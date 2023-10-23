package shepherd.birdup.data;

import org.springframework.transaction.annotation.Transactional;
import shepherd.birdup.models.AppUser;

import java.util.List;

public interface AppUserRepository {
    @Transactional
    AppUser findByUsername(String username);

    @Transactional
    AppUser findById(int id);

    @Transactional
    AppUser add(AppUser user);

    @Transactional
    void update(AppUser user);

    void updateRoles(AppUser user);

    List<String> getRolesByUsername(String username);
    List<String> getRolesById(int id);


}
