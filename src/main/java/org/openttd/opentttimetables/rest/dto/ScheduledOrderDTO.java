package org.openttd.opentttimetables.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ScheduledOrderDTO {
    private String destination;
    private LocalTime arrival;
    private LocalTime departure;
}