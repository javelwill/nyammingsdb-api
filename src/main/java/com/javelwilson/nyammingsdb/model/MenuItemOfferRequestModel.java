package com.javelwilson.nyammingsdb.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MenuItemOfferRequestModel {
    private String name;

    private BigDecimal price;
}
