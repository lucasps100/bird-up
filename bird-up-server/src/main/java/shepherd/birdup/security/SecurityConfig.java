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

        http.csrf().disable();

        http.cors();


        http.authorizeRequests()
                .antMatchers("/api/birdup/authenticate").permitAll()
                .antMatchers("/api/birdup/register").permitAll()
                .antMatchers("/api/birdup/login").permitAll()
                .antMatchers("/api/birdup/refresh-token").authenticated()
                .antMatchers(HttpMethod.GET,
                        "/api/birdup/post", "/api/birdup/post/*/*/*", "/api/birdup/profile/*", "/api/birdup/location", "/api/birdup/location/*", "/api/birdup/state", "/api/birdup/state/*", "/api/birdup/species", "/api/birdup/species/*", "/api/birdup/comment", "/api/birdup/comment/*").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/birdup/follower/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST,
                        "/api/birdup/post/**", "/api/birdup/like", "/api/birdup/comment", "/api/birdup/follower/**", "/api/birdup/profile", "/api/birdup/location/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT,
                        "/api/birdup/post/*/*", "/api/birdup/profile", "/api/birdup/comment/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE,
                        "/api/birdup/like/*", "/api/birdup/follower/**", "/api/birdup/comment/*", "/api/birdup/profile", "/api/birdup/post/*").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/api/birdup/location").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/birdup/location").hasAuthority("ADMIN")
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
