package shepherd.birdup.domain;

import org.springframework.stereotype.Service;
import shepherd.birdup.data.ProfileRepository;
import shepherd.birdup.models.Profile;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class ProfileService {

    private final ProfileRepository repository;

    private final Validator validator;


    public ProfileService(ProfileRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Profile findById(int appUserId) {
        return repository.findById(appUserId);
    }

    public Profile findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public List<Profile> findByPartialName(String partialName) {
        return repository.findByPartialName(partialName);
    }

    public Result<Profile> create(Profile profile) {
        Result<Profile> result = new Result<>();
        if (profile == null) {
            result.addMessage(ResultType.INVALID, "nothing to add");
            return result;
        }
        if (profile.getAppUserId() < 1) {
            result.addMessage(ResultType.INVALID, "profile id should be set");
            return result;
        }
        if (repository.findById(profile.getAppUserId()) != null) {
            result.addMessage(ResultType.INVALID, "profile already exists for this account");
            return result;
        }

        result = validateProfile(profile);
        if (result.isSuccess()) {
            profile = repository.createProfile(profile);
            result.setPayload(profile);
        }
        return result;
    }

    public Result<Profile> update(Profile profile) {
        Result<Profile> result = new Result<>();
        if (profile == null) {
            result.addMessage(ResultType.INVALID, "nothing to update");
            return result;
        }
        result = validateProfile(profile);
        if (!result.isSuccess()) {
            return result;
        }

        if (profile.getAppUserId() < 1) {
            result.addMessage(ResultType.INVALID, "profile id is required");
            return result;
        }
        if (!repository.updateProfile(profile)) {
            result.addMessage(ResultType.NOT_FOUND, String.format("profile %s not found", profile.getAppUserId()));
        }
        return result;
    }

    public Result<Profile> softDeleteById(int appUserId) {
        Result<Profile> result = new Result<>();
        if (!repository.softDeleteById(appUserId)) {
            String msg = String.format("profile %s not found", appUserId);
            result.addMessage(ResultType.NOT_FOUND, msg);
        }
        return result;
    }

    public Result<Profile> restoreProfileById(int appUserId) {
        Result<Profile> result = new Result<>();
        if (!repository.restoreProfileById(appUserId)) {
            String msg = String.format("profile %s not found", appUserId);
            result.addMessage(ResultType.NOT_FOUND, msg);
        }
        return result;
    }


    private Result<Profile> validateProfile(Profile profile) {
        Result<Profile> result = new Result<>();

        Set<ConstraintViolation<Profile>> violations = validator.validate(profile);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<?> violation : violations) {
                result.addMessage(ResultType.INVALID, violation.getMessage());
            }
        }
        return result;
    }

}
