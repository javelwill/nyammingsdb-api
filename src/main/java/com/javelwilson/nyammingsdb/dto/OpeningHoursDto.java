package com.javelwilson.nyammingsdb.dto;

import lombok.Data;

import java.sql.Time;
import java.time.DayOfWeek;

@Data
public class OpeningHoursDto {
    private long id;

    private DayOfWeek dayOfWeek;

    private Time opens;

    private Time closes;

    private LocationDto location;
}
