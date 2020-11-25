package com.javelwilson.nyammingsdb.security;

import com.javelwilson.nyammingsdb.entity.UserEntity;
import com.javelwilson.nyammingsdb.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final SecurityConstants securityConstants;

    private final UserRepository userRepository;

    public AuthorizationFilter(AuthenticationManager authManager, SecurityConstants securityConstants, UserRepository userRepository) {
        super(authManager);
        this.securityConstants = securityConstants;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(securityConstants.getHeader());

        if (header == null || !header.startsWith(securityConstants.getTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(securityConstants.getHeader());

        if (token != null) {

            token = token.replace(securityConstants.getTokenPrefix(), "");

            String user = Jwts.parser()
                    .setSigningKey( securityConstants.getSecret() )
                    .parseClaimsJws( token )
                    .getBody()
                    .getSubject();

            if (user != null) {
                UserEntity userEntity = userRepository.findByEmail(user);
                UserPrincipal userPrincipal = new UserPrincipal(userEntity);
                return new UsernamePasswordAuthenticationToken(user, null, userPrincipal.getAuthorities());
            }

            return null;
        }

        return null;
    }

}
