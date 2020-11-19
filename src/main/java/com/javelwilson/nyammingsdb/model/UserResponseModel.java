package com.javelwilson.nyammingsdb.model;

import lombok.Data;

@Data
public class UserResponseModel {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
