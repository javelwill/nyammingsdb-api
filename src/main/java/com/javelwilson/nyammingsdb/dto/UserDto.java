package com.javelwilson.nyammingsdb.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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
    private List<ApplicationDto> applications;
    private Collection<String> roles;
    private String address;
    private Integer postCode;
    private String city;
    private String state;
    private String country;
    private String company;
    private String website;
}
