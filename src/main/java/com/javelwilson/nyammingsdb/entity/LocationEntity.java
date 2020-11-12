package com.javelwilson.nyammingsdb.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "locations")
public class LocationEntity implements Serializable {

    private static final long serialVersionUID = 3966896722566857747L;

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 30, nullable = false)
    private String locationId;

    @Column(length = 50, nullable = false)
    private String city;

    @Column(length = 100, nullable = false)
    private String streetName;

    private BigDecimal starRating;

    private String photo1;

    private String photo2;

    private String photo3;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<OpeningHoursEntity> openingHours;

    @ManyToOne
    @JoinColumn(name="restaurant_id")
    private RestaurantEntity restaurant;
}
