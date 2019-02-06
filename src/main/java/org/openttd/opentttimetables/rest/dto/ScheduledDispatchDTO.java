package org.openttd.opentttimetables.rest.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class ScheduledDispatchDTO {
    private Integer id;
    private Integer timetableId;
    private String timetable;

    @Min(value = 1, message = "Repetition may not be infite")
    @Max(value = 1440, message = "Can repeat at most once a day")
    private Integer intervalInMinutes;

    @Size(min = 1)
    private List<Integer> departures;
}
