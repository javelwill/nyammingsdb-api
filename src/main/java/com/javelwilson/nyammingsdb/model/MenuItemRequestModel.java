package com.javelwilson.nyammingsdb.model;

import com.javelwilson.nyammingsdb.dto.MenuItemOfferDto;
import com.javelwilson.nyammingsdb.dto.MenuSectionDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MenuItemRequestModel {
    private String name;

    private String description;

    private BigDecimal price;

    private String image;

    private List<MenuItemOfferRequestModel> offers;
}
