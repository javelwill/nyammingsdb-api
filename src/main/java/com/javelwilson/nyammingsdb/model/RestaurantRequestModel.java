package com.javelwilson.nyammingsdb.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class RestaurantRequestModel {

    @NotBlank(message = "name is required")
    private String name;

    private String description;

    private String logo;

    @Min(value = 0, message = "starRating must be between 0 and 5")
    @Max(value = 5, message = "starRating must be between 0 and 5")
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

    private List<LocationRequestModel> locations;

    private List<MenuItemRequestModel> menus;
}
