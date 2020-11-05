package com.javelwilson.nyammingsdb.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RestaurantRequestModel {

    @NotBlank(message = "name is required")
    private String name;

    private String description;

    private String logo;

    private Integer rating;
}
