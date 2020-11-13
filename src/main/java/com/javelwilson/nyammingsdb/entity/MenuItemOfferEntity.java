package com.javelwilson.nyammingsdb.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "menu_item_offers")
public class MenuItemOfferEntity {
    @Id
    @GeneratedValue
    private long id;

    private String offerId;

    private String name;

    private BigDecimal price;

    private String description;

    private String type;

    private Boolean required;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItemEntity menuItem;
}
