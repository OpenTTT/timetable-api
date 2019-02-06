package org.openttd.opentttimetables.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ScheduledOrderDTO {
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private String destination;
    private String arrival;
    private String departure;
}