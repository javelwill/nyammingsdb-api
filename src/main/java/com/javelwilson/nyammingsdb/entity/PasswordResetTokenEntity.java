package com.javelwilson.nyammingsdb.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetTokenEntity implements Serializable {
    private static final long serialVersionUID = -2742418059295133646L;

    @Id
    @GeneratedValue
    private long id;

    private String token;

    @OneToOne()
    @JoinColumn(name="users_id")
    private UserEntity users;
}
