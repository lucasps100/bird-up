package shepherd.birdup.security;


import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shepherd.birdup.data.AppUserRepository;
import shepherd.birdup.domain.Result;
import shepherd.birdup.domain.ResultType;
import shepherd.birdup.models.AppUser;

import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository repository;
    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository repository,
                          PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Use the repository.
        AppUser appUser = repository.findByUsername(username);

        // 2. Two failure scenarios.
        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException(username + " not found");
        }

        // 3. Must return a UserDetails interface.
        return appUser;
    }

    public Result<AppUser> create(String username, String password) {
        Result<AppUser> result = validate(username, password);
        if (!result.isSuccess()) {
            return result;
        }

        password = encoder.encode(password);

        AppUser appUser = new AppUser(0, username, password, true, List.of("USER"));

        try {
            appUser = repository.create(appUser);
            result.setPayload(appUser);
        } catch (DuplicateKeyException e) {
            result.addMessage(ResultType.INVALID, "The provided username already exists");
        }

        return result;
    }

    private Result<AppUser> validate(String username, String password) {
        Result<AppUser> result = new Result<>();
        if (username == null || username.isBlank()) {
            result.addMessage(ResultType.INVALID, "username is required");
            return result;
        }

        if (password == null) {
            result.addMessage(ResultType.INVALID, "password is required");
            return result;
        }

        if (username.length() > 50) {
            result.addMessage(ResultType.INVALID, "username must be less than 50 characters");
        }

        if (!isValidPassword(password)) {
            result.addMessage(ResultType.INVALID,
                    "password must be at least 8 character and contain a digit," +
                            " a letter, and a non-digit/non-letter");
        }

        return result;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        return digits > 0 && letters > 0 && others > 0;
    }
}
