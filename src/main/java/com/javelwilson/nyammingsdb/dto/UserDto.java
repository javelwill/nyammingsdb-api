package com.javelwilson.nyammingsdb.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
public class UserDto implements Serializable {
    private static final long serialVersionUID = -7187026302240067764L;
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerifcationStatus = false;
    private Collection<String> roles;
}
