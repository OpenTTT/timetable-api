package org.openttd.opentttimetables.model;

import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Builder
public class ScheduledDispatch {
    private List<TimetabledOrder> orders;
    private Integer interval;
    private LocalTime startTime;

}
