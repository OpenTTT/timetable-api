package org.openttd.opentttimetables.timetabling;

import org.openttd.opentttimetables.model.Schedule;
import org.openttd.opentttimetables.model.ScheduledDispatch;

import java.util.List;

public class Schedules {
    public static List<Schedule> getScheduleFor(ScheduledDispatch dispatch) {
        return dispatch.schedulesForInterval();
    }
}
