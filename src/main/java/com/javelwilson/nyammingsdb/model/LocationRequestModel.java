package com.javelwilson.nyammingsdb.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LocationRequestModel {

    @NotBlank(message = "streetName is required")
    private String streetName;

    @NotBlank(message = "city is required")
    private String city;
}
