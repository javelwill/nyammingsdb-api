package com.javelwilson.nyammingsdb.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MenuItemResponseModel {
    private String menuItemId;

    private String name;

    private String description;

    private BigDecimal price;

    private String photo1;

    private String photo2;

    private String photo3;

    private List<MenuItemOfferResponseModel> offers;
}
