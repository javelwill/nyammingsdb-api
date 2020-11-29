package com.javelwilson.nyammingsdb.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "applications")
public class ApplicationEntity implements Serializable {
    private static final long serialVersionUID = -6423551821661335438L;

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String description;

    private String applicationId;

    private String applicationKey;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
