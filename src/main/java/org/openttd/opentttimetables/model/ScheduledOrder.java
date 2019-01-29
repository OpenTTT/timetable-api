package org.openttd.opentttimetables.model;

import lombok.Data;

import java.time.LocalTime;

/**
 * A single order in a Schedule
 */
@Data
public class ScheduledOrder {
    private TimetabledOrder order;
    private LocalTime arrival;

    public ScheduledOrder(TimetabledOrder order, LocalTime arrival) {
        this.order = order;
        this.arrival = arrival;
    }

    public LocalTime getArrival() {
        return arrival;
    }

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
