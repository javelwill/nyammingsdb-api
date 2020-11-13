package com.javelwilson.nyammingsdb.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "restaurants")
public class RestaurantEntity implements Serializable {

    private static final long serialVersionUID = -3165411496179580749L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String restaurantId;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private String logo;

    private String slogan;

    private Double starRating;

    private String photo1;

    private String photo2;

    private String photo3;

    private Boolean acceptsReservations;

    private Boolean hasMenu;

    private Boolean hasPos;

    private Double priceRange;

    private String email;

    private String telephone;

    private String faxNumber;

    private String address;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<LocationEntity> locations;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<MenuEntity> menus;
}
