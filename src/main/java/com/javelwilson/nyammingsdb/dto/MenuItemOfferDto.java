package com.javelwilson.nyammingsdb.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MenuItemOfferDto {
    private long id;

    private String offerId;

    private String name;

    private BigDecimal price;

    private String description;

    private String type;

    private Boolean required;

    private MenuItemDto menuItem;
}
