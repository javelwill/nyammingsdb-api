package com.javelwilson.nyammingsdb.model;

import lombok.Data;

import java.util.List;

@Data
public class LocationResponseModel {

    private String locationId;

    private String city;

    private String streetName;

    private List<OpeningHoursRequestModel> openingHours;

}
