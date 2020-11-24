package com.javelwilson.nyammingsdb.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "authorities")
public class AuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Collection<RoleEntity> roles;
}
