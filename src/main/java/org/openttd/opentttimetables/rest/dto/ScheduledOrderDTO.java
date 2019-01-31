package org.openttd.opentttimetables.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduledOrderDTO {
    private String destination;
    private String arrival;
    private String departure;
}