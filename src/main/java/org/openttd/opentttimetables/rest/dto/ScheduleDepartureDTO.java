package org.openttd.opentttimetables.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
@Setter
public class ScheduleDepartureDTO {
    private LocalTime arrival;
    private LocalTime departure;
}
