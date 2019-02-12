package org.openttt.scheduling;

import com.google.common.collect.Lists;
import org.openttt.model.Schedule;
import org.openttt.model.ScheduledOrder;
import org.openttt.model.Timetable;
import org.openttt.model.TimetabledOrder;

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
