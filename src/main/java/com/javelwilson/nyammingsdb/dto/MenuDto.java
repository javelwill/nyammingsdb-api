package com.javelwilson.nyammingsdb.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuDto {
    private long id;

    private String menuId;

    private String name;

    private String url;

    private List<MenuSectionDto> menuSections;

    private RestaurantDto restaurant;
}
