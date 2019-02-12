package org.openttt.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatsDTO {
    private Long stations;
    private Long scheduledDispatches;
    private Long totalDepartures;
    private Long totalTimetables;
}
