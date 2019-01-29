package org.openttd.opentttimetables.timetabling;

import org.openttd.opentttimetables.model.Schedule;
import org.openttd.opentttimetables.model.ScheduledDispatch;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Schedules {
    public static List<Schedule> getScheduleFor(ScheduledDispatch dispatch) {
        return dispatch.schedulesForInterval();
    }

    public static List<Schedule> nextTwentyFive(ScheduledDispatch dispatch) {
        return dispatch.schedules()
                .limit(25)
                .collect(Collectors.toList());
    }
}
