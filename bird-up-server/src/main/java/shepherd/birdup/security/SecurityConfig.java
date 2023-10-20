package shepherd.birdup.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
    // SecurityFilterChain allows configuring
    // web based security for specific http requests.

    private final JwtConverter converter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtConverter converter, UserDetailsService userDetailsService) {
        this.converter = converter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {
        // we're not using HTML forms in our app
        //so disable CSRF (Cross Site Request Forgery)
        http.csrf().disable();

        // this configures Spring Security to allow
        //CORS related requests (such as preflight checks)
        http.cors();

        // the order of the antMatchers() method calls is important
        // as they're evaluated in the order that they're added
        http.authorizeRequests()
                .antMatchers("/api/bird-up/authenticate").permitAll()
                .antMatchers("/api/bird-up/create_account", "api/bird-up/profile/create").permitAll()
                .antMatchers("/api/bird-up/refresh_token").authenticated()
                .antMatchers(HttpMethod.GET,
                        "/api/bird-up/post", "/api/bird-up/post/*", "/api/bird-up/post/*", "/api/bird-up/profile/*").permitAll()
                .antMatchers(HttpMethod.POST,
                        "/api/bird-up/post", "/api/bird-up/like/*", "/api/bird-up/comment/*", "/api/bird-up/follow/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT,
                        "/api/bird-up/post/*", "/api/bird-up/profile/*", "/api/bird-up/comment/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE,
                        "/api/bird-up/like/*", "/api/bird-up/comment/*").hasAnyAuthority("ADMIN", "USER")
                // if we get to this point, let's deny all requests
                .antMatchers("/**").denyAll()
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(authConfig), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
