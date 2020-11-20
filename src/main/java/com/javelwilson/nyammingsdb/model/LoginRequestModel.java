package com.javelwilson.nyammingsdb.model;

import lombok.Data;

@Data
public class LoginRequestModel {
    private String email;
    private String password;
}
