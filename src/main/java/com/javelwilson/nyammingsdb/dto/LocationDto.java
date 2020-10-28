package com.javelwilson.nyammingsdb.dto;

import lombok.Data;

@Data
public class LocationDto {
    private long id;
    private String locationId;
    private String city;
    private String streetName;
    private RestaurantDto restaurant;
}
