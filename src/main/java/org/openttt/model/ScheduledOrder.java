package org.openttt.model;

import lombok.Getter;

import java.time.LocalTime;

/**
 * A single order in a Schedule
 */
@Getter
public class ScheduledOrder {
    /**
     * The underlying order, containing the actual routing and timetabling information.
     */
    private TimetabledOrder timetabledOrder;

    /**
     * The arrival time from the previous {@link ScheduledOrder} (or start of the {@link Schedule}, if there isn't one).
     */
    private LocalTime arrival;

    public ScheduledOrder(TimetabledOrder timetabledOrder, LocalTime arrival) {
        this.timetabledOrder = timetabledOrder;
        this.arrival = arrival;
    }

    /**
     * @return The calculated departure time based on the staying time of the {@link TimetabledOrder}
     */
    public LocalTime getDeparture() {
        return arrival.plusMinutes(timetabledOrder.getStayingTime());
    }

    public Destination getDestination() {
        return timetabledOrder.getDestination();
    }

}
