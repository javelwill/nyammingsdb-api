package com.javelwilson.nyammingsdb.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "menus")
public class MenuEntity {

    @Id
    @GeneratedValue
    private long id;

    private String menuId;

    private String name;

    private String url;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuSectionEntity> menuSections;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

}
