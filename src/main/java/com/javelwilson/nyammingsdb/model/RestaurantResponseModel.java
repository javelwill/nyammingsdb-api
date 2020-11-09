package com.javelwilson.nyammingsdb.model;

import lombok.Data;

import java.util.List;

@Data
public class RestaurantResponseModel {

    private String restaurantId;

    private String name;

    private String description;

    private String logo;

    private Double starRating;

    private Boolean acceptsReservations;

    private Boolean hasMenu;

    private Boolean hasPos;

    private Integer priceRange;

    private String email;

    private String telephone;

    private String faxNumber;

    private String slogan;

    private String address;

    private List<RestaurantPhotosResponseModel> photos;

    private List<MenuResponseModel> menus;

    private List<LocationResponseModel> locations;
}
