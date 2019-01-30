package org.openttd.opentttimetables.scheduling;

import org.openttd.opentttimetables.model.Schedule;
import org.openttd.opentttimetables.model.ScheduledDispatch;

import java.time.LocalTime;
import java.util.function.Supplier;

public class ScheduleSupplier implements Supplier<Schedule> {
    private LocalTime intervalStartTime = LocalTime.MIDNIGHT;
    private LocalTime currentTime = LocalTime.MIDNIGHT;
    private ScheduledDispatch scheduledDispatch;

    /**
     * The current index of departures we are looking at, cycling back to 0 once all departures of a ScheduledDispatch
     * have been finished
     */
    private int currentOffsetIndex = 0;

    public ScheduleSupplier(ScheduledDispatch scheduledDispatch) {
        this.scheduledDispatch = scheduledDispatch;
    }

    @Override
    public Schedule get() {
        DepartureScheduler scheduler = new DepartureScheduler(currentTime, scheduledDispatch.getTimetable());

        if (++currentOffsetIndex < scheduledDispatch.getDepartures().size()) {
            currentTime = currentTime.plusMinutes(offsetForCurrentIndex());
        } else {
            // Roll over into next interval
            intervalStartTime = intervalStartTime.plusMinutes(scheduledDispatch.getIntervalInMinutes());

            // Reset index
            currentOffsetIndex = 0;

            // Adjust next for next iteration being at start of next interval + offset
            currentTime = intervalStartTime.plusMinutes(offsetForCurrentIndex());
        }

        return scheduler.schedule();
    }

    private Integer offsetForCurrentIndex() {
        return scheduledDispatch.getDepartures().get(currentOffsetIndex);
    }
}
