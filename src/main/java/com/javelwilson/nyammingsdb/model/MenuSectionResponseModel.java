package com.javelwilson.nyammingsdb.model;

import com.javelwilson.nyammingsdb.dto.MenuItemDto;
import lombok.Data;

import java.util.List;

@Data
public class MenuSectionResponseModel {
    private String menuSectionId;

    private String name;

    private String description;

    private List<MenuItemResponseModel> menuItems;
}
