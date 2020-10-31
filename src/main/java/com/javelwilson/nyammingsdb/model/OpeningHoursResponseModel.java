package com.javelwilson.nyammingsdb.model;

import lombok.Data;

import java.sql.Time;
import java.time.DayOfWeek;

@Data
public class OpeningHoursResponseModel {
    private DayOfWeek dayOfWeek;

    private Time opens;

    private Time closes;
}
