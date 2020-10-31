package com.javelwilson.nyammingsdb.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Time;
import java.time.DayOfWeek;

@Data
public class OpeningHoursRequestModel {

    private DayOfWeek dayOfWeek;

    private Time opens;

    private Time closes;
}
