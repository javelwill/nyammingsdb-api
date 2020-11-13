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

    private Double starRating;

    private String photo1;

    private String photo2;

    private String photo3;

    private Boolean acceptsReservations;

    private Boolean hasMenu;

    private Boolean hasPos;

    private Double priceRange;

    private String email;

    private String telephone;

    private String faxNumber;

    private String slogan;

    private String address;

    private List<LocationDto> locations;

    private List<MenuDto> menus;
}
