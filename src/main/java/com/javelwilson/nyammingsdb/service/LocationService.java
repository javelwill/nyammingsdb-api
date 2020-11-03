package com.javelwilson.nyammingsdb.service;

import com.javelwilson.nyammingsdb.dto.LocationDto;

import java.util.List;

public interface LocationService {
    LocationDto getLocation(String restaurantId, String locationId);
    List<LocationDto> getLocations(String restaurantId);
}
