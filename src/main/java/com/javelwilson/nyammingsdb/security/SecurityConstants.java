package com.javelwilson.nyammingsdb.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityConstants {
    private String secret;
    private String tokenPrefix;
    private String header;
    private long expirationTime;
    private long passwordResetExpiration;
}
