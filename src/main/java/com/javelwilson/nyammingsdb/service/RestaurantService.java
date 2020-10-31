package com.javelwilson.nyammingsdb.service;

import com.javelwilson.nyammingsdb.dto.RestaurantDto;

import java.util.List;

public interface RestaurantService {
    RestaurantDto createRestaurant(RestaurantDto restaurantDto);
    List<RestaurantDto> getRestaurants(int page, int limit);
}
