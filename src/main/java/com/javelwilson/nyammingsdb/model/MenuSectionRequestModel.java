package com.javelwilson.nyammingsdb.model;

import lombok.Data;

import java.util.List;

@Data
public class MenuSectionRequestModel {
    private String name;

    private String description;

    private List<MenuItemRequestModel> menuItems;
}
