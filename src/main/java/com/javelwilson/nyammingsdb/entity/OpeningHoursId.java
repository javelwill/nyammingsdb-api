package com.javelwilson.nyammingsdb.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.DayOfWeek;

@Data
public class OpeningHoursId implements Serializable {
    private DayOfWeek dayOfWeek;
    private long location;
}
