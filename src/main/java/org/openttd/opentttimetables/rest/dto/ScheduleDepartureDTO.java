package org.openttd.opentttimetables.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ScheduleDepartureDTO {
    private String arrival;
    private String departure;
}
