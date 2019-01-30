package org.openttd.opentttimetables.scheduling;

import com.google.common.collect.Lists;
import org.openttd.opentttimetables.model.Schedule;
import org.openttd.opentttimetables.model.ScheduledOrder;
import org.openttd.opentttimetables.model.Timetable;
import org.openttd.opentttimetables.model.TimetabledOrder;

import java.time.LocalTime;
import java.util.List;

/**
 * Schedules a single departure from a ScheduledDispatch.
 */
public class DepartureScheduler {
    private LocalTime startTime;
    private Timetable timetable;

    public DepartureScheduler(LocalTime startTime, Timetable timetable) {
        this.startTime = startTime;
        this.timetable = timetable;
    }

    public Schedule schedule() {
        LocalTime currentTime = LocalTime.from(startTime);
        List<ScheduledOrder> scheduledOrders = Lists.newArrayListWithExpectedSize(timetable.getOrders().size());

        for (TimetabledOrder timetabledOrder : timetable.getOrders()) {
            scheduledOrders.add(new ScheduledOrder(timetabledOrder, currentTime));

            currentTime = currentTime
                    .plusMinutes(timetabledOrder.getStayingTime())
                    .plusMinutes(timetabledOrder.getTravelingTime());
        }

        return new Schedule(scheduledOrders);
    }
}
