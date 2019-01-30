package org.openttd.opentttimetables.model;

import java.time.LocalTime;

/**
 * A single order in a Schedule
 */
public class ScheduledOrder {
    /**
     * The underlying order, containing the actual routing and timetabling information.
     */
    private TimetabledOrder order;

    /**
     * The arrival time from the previous {@link ScheduledOrder} (or start of the {@link Schedule}, if there isn't one).
     */
    private LocalTime arrival;

    public ScheduledOrder(TimetabledOrder order, LocalTime arrival) {
        this.order = order;
        this.arrival = arrival;
    }

    public LocalTime getArrival() {
        return arrival;
    }

    /**
     * @return The calculated departure time based on the staying time of the {@link TimetabledOrder}
     */
    public LocalTime getDeparture() {
        return arrival.plusMinutes(order.getStayingTime());
    }

    public Destination getDestination() {
        return order.getDestination();
    }

    public TimetabledOrder getTimetabledOrder() {
        return order;
    }
}
