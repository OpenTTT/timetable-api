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
    private Boolean withReturnOrder;


    public DepartureScheduler(LocalTime startTime, Timetable timetable) {
        this(startTime, timetable, false);
    }

    public DepartureScheduler(LocalTime startTime, Timetable timetable, Boolean withReturnOrder) {
        this.startTime = startTime;
        this.timetable = timetable;
        this.withReturnOrder = withReturnOrder;
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

        Schedule schedule = new Schedule(scheduledOrders);

        if (withReturnOrder) {
            return schedule.withReturnOrder();
        } else {
            return schedule;
        }
    }
}
