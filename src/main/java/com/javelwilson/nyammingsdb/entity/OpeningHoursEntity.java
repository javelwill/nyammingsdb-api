package com.javelwilson.nyammingsdb.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.DayOfWeek;

@Data
@Entity
@Table(name = "opening_hours")
public class OpeningHoursEntity implements Serializable {

    private static final long serialVersionUID = -8721099435374412211L;

    @Id
    @GeneratedValue
    private long id;

    private DayOfWeek dayOfWeek;

    private Time opens;

    private Time closes;

    @ManyToOne
    @JoinColumn(name="location_id")
    private LocationEntity location;
}
