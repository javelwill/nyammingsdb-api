package com.javelwilson.nyammingsdb.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class RestaurantRequestModel {

    @NotBlank(message="name is required")
    private String name;

    @NotBlank(message="description is required")
    private String description;

    private String logo;

    private int rating;

    @Valid
    private List<LocationRequestModel> locations;
}
