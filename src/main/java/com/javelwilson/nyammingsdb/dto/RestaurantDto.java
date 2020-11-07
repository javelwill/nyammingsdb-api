package com.javelwilson.nyammingsdb.dto;

import lombok.Data;

import java.util.List;

@Data
public class RestaurantDto {
    private long id;

    private String restaurantId;

    private String name;

    private String description;

    private String logo;

    private Integer rating;

    private List<LocationDto> locations;

    private List<MenuDto> menus;
}
