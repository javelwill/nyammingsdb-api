package com.javelwilson.nyammingsdb.model;

import lombok.Data;

import java.util.List;

@Data
public class RestaurantResponseModel {

    private String restaurantId;

    private String name;

    private String description;

    private String logo;

    private int rating;

    private List<LocationResponseModel> locations;

    private List<MenuResponseModel> menus;
}
