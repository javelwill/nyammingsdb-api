package com.javelwilson.nyammingsdb.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class LocationDto {
    private long id;

    private String locationId;

    private String city;

    private String streetName;

    private BigDecimal starRating;

    private String photo1;

    private String photo2;

    private String photo3;

    private RestaurantDto restaurant;

    private List<OpeningHoursDto> openingHours;
}
