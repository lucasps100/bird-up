package shepherd.birdup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shepherd.birdup.domain.Result;
import shepherd.birdup.models.AppUser;
import shepherd.birdup.security.AppUserService;
import shepherd.birdup.security.JwtConverter;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtConverter converter;
    private final AppUserService appUserService;


    public AuthController(AuthenticationManager authenticationManager, JwtConverter converter, AppUserService appUserService) {
        this.authenticationManager = authenticationManager;
        this.converter = converter;
        this.appUserService = appUserService;
    }

    @PostMapping("/refresh_token")
    // new... inject our `AppUser`, set by the `JwtRequestFilter`
    public ResponseEntity<Map<String, String>> refreshToken(@AuthenticationPrincipal AppUser appUser) {
        String jwtToken = converter.getTokenFromUser(appUser);

        HashMap<String, String> map = new HashMap<>();
        map.put("jwt_token", jwtToken);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody Map<String, String> credentials) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password"));

        try {
            Authentication authentication = authenticationManager.authenticate(authToken);

            if (authentication.isAuthenticated()) {
                String jwtToken = converter.getTokenFromUser((AppUser) authentication.getPrincipal());

                HashMap<String, String> map = new HashMap<>();
                map.put("jwt_token", jwtToken);

                return new ResponseEntity<>(map, HttpStatus.OK);
            }

        } catch (AuthenticationException ex) {
            System.out.println(ex);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/create_account")
    public ResponseEntity<?> createAccount(@RequestBody Map<String, String> credentials) {

        String username = credentials.get("username");
        String password = credentials.get("password");

        Result<AppUser> result = appUserService.create(username, password);

        // unhappy path...
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        // happy path...
        HashMap<String, Integer> map = new HashMap<>();
        map.put("appUserId", result.getPayload().getId());

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
}
