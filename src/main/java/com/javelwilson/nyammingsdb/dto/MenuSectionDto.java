package com.javelwilson.nyammingsdb.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuSectionDto {
    private long id;

    private String menuSectionId;

    private String name;

    private String description;

    private List<MenuItemDto> menuItems;

    private MenuDto menu;
}
