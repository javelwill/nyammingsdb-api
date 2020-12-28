package com.javelwilson.nyammingsdb.security;

import com.javelwilson.nyammingsdb.repository.UserRepository;
import com.javelwilson.nyammingsdb.service.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecurityConstants securityConstants;
    private final UserRepository userRepository;

    public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, SecurityConstants securityConstants, UserRepository userRepository) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.securityConstants = securityConstants;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                .regexMatchers("\\/v2\\/api-docs\\?group=internal")
                .hasRole("ADMIN")
                .mvcMatchers(
                        HttpMethod.GET,
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html**",
                        "/webjars/**",
                        "favicon.ico"
                ).permitAll()
                .mvcMatchers("/h2-console/**")
                .permitAll()
                .mvcMatchers("/actuator/**")
                .permitAll()
                .mvcMatchers(HttpMethod.POST, "/users")
                .permitAll()
                .mvcMatchers(HttpMethod.GET, "/users")
                .hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET, "/users/email-verification")
                .permitAll().
                mvcMatchers(HttpMethod.POST, "/users/password-reset-request")
                .permitAll().
                mvcMatchers(HttpMethod.POST, "/users/reset-password")
                .permitAll()
                .mvcMatchers(HttpMethod.DELETE, "/users/**")
                .hasAuthority("DELETE_AUTHORITY")
                .mvcMatchers(HttpMethod.GET, "/api/restaurants/**")
                .permitAll()
                .mvcMatchers("/restaurants")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager(), securityConstants))
                .addFilter(new AuthorizationFilter(authenticationManager(), securityConstants, userRepository))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
