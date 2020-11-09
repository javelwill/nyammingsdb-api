package com.javelwilson.nyammingsdb.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "restaurant_photos")
public class RestaurantPhotosEntity implements Serializable {
    private static final long serialVersionUID = -1974685936333123894L;

    @Id
    @GeneratedValue
    private Long id;

    private String restaurantPhotoId;

    private String name;

    private String description;

    @ManyToOne()
    @JoinColumn(name="restaurant_id")
    private RestaurantEntity restaurant;

}
