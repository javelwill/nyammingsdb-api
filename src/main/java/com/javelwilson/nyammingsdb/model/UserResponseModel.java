package com.javelwilson.nyammingsdb.model;

import lombok.Data;

@Data
public class UserResponseModel {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Integer postCode;
    private String city;
    private String state;
    private String country;
    private String company;
    private String website;
}
