package com.javelwilson.nyammingsdb.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class LocationRequestModel {

    @NotBlank(message = "streetName is required")
    private String streetName;

    @NotBlank(message = "city is required")
    private String city;

    @Valid
    private List<OpeningHoursRequestModel> openingHours;
}
