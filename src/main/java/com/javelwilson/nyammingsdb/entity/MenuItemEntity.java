package com.javelwilson.nyammingsdb.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "menu_items")
public class MenuItemEntity {

    @Id
    @GeneratedValue
    private long id;

    private String menuItemId;

    private String name;

    private String description;

    private BigDecimal price;

    private String photo1;

    private String photo2;

    private String photo3;

    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL)
    private List<MenuItemOfferEntity> offers;

    @ManyToOne
    @JoinColumn(name = "menu_section_id")
    private MenuSectionEntity menuSection;
}
