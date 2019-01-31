package org.openttd.opentttimetables.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScheduledDispatchDTO {
    private Integer id;
    private Integer timetableId;
    private Integer intervalInMinutes;
    private List<Integer> departures;
    private String timetable;
}
