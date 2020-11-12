package com.javelwilson.nyammingsdb.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "photos")
public class PhotoEntity implements Serializable {
    private static final long serialVersionUID = -1974685936333123894L;

    @Id
    @GeneratedValue
    private Long id;

    private String photoId;

    private String url;
}
