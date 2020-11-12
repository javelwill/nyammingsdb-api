package com.javelwilson.nyammingsdb.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Data
public class LocationRequestModel {

    @NotBlank(message = "streetName is required")
    private String streetName;

    @NotBlank(message = "city is required")
    private String city;

    private BigDecimal starRating;

    private String photo1;

    private String photo2;

    private String photo3;

    @Valid
    private List<OpeningHoursRequestModel> openingHours;
}
