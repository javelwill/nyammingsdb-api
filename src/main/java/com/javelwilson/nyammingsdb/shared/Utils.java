package com.javelwilson.nyammingsdb.shared;

import com.javelwilson.nyammingsdb.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Service
public class Utils {

    @Autowired
    private SecurityConstants securityConstants;

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public  boolean hasTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(securityConstants.getSecret())
                .parseClaimsJws(token).getBody();

        Date tokenExpirationDate = claims.getExpiration();
        Date todayDate = new Date();

        return tokenExpirationDate.before(todayDate);
    }

    public String generateEmailVerificationToken(String userId) {
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + securityConstants.getExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, securityConstants.getSecret())
                .compact();
        return token;
    }

    public String generateUserId(int length) {
        return generateRandomString(length);
    }

    public String generateRestaurantId(int length) {
        return generateRandomString(length);
    }

    public String generateLocationId(int length) {
        return generateRandomString(length);
    }

    public String generateMenuId(int length) {
        return generateRandomString(length);
    }

    public String generateMenuSectionId(int length) {
        return generateRandomString(length);
    }

    public String generateMenuItemId(int length) {
        return generateRandomString(length);
    }

    public String generateMenuOfferId(int length) {
        return generateRandomString(length);
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }
}
