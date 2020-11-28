package com.javelwilson.nyammingsdb.model;

import lombok.Data;

@Data
public class ResetPasswordRequestModel {
    private String token;
    private String password;
}
