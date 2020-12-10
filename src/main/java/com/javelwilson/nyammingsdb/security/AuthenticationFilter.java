package com.javelwilson.nyammingsdb.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javelwilson.nyammingsdb.SpringApplicationContext;
import com.javelwilson.nyammingsdb.dto.UserDto;
import com.javelwilson.nyammingsdb.model.LoginRequestModel;
import com.javelwilson.nyammingsdb.service.IUserService;
import com.javelwilson.nyammingsdb.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final SecurityConstants securityConstants;

    public AuthenticationFilter(AuthenticationManager authenticationManager, SecurityConstants securityConstants) {
        this.authenticationManager = authenticationManager;
        this.securityConstants = securityConstants;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequestModel loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword(),
                    new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String username = ((UserPrincipal) authResult.getPrincipal()).getUsername();

        String token = Jwts.builder()
                    .setSubject(username)
                    .setExpiration(new Date(System.currentTimeMillis() + securityConstants.getExpirationTime()))
                    .signWith(SignatureAlgorithm.HS512, securityConstants.getSecret())
                    .compact();

        UserService userService = (UserService) SpringApplicationContext.getBean("userService");
        UserDto userDto = userService.getUserByEmail(username);

        HashMap userInfo = new HashMap();
        userInfo.put("token", securityConstants.getTokenPrefix() + token);
        userInfo.put("userId", userDto.getUserId());
        userInfo.put("firstName", userDto.getFirstName());
        userInfo.put("lastName", userDto.getLastName());

        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        response.getOutputStream().print(new ObjectMapper().writeValueAsString(userInfo));
    }
}
