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

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String logo;

    private Integer rating;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<LocationEntity> locations;
}
