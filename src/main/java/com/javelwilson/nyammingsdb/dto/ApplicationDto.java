package com.javelwilson.nyammingsdb.dto;

import lombok.Data;

@Data
public class ApplicationDto {
    private long id;

    private String name;

    private String description;

    private String applicationId;

    private String applicationKey;

    private UserDto user;
}
