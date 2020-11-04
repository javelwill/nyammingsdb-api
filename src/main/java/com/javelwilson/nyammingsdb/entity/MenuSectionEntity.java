package com.javelwilson.nyammingsdb.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "menu_sections")
public class MenuSectionEntity {

    @Id
    @GeneratedValue
    private long id;

    private String menuSectionId;

    private String name;

    private String description;

    @OneToMany(mappedBy = "menuSection", cascade = CascadeType.ALL)
    private List<MenuItemEntity> menuItems;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private MenuEntity menu;
}
