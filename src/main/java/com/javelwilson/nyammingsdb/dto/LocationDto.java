package com.javelwilson.nyammingsdb.dto;

import com.javelwilson.nyammingsdb.entity.OpeningHoursEntity;
import lombok.Data;

import java.util.List;

@Data
public class LocationDto {
    private long id;

    private String locationId;

    private String city;

    private String streetName;

    private RestaurantDto restaurant;

    private List<OpeningHoursDto> openingHours;

}
