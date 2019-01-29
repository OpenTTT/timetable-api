package org.openttd.opentttimetables.model;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

/**
 * A single vehicle schedule, i.e. the times it arrives at every stations.
 *
 * Corresponds to a single departure for a single interval of a ScheduledDispatch
 */
public class Schedule {
    private List<ScheduledOrder> orders;

    public Schedule(List<ScheduledOrder> orders) {
        this.orders = orders;
    }

    public LocalTime getFirstDeparture() {
        return orders.get(0).getDeparture();
    }

    public List<ScheduledOrder> getOrders() {
        return orders;
    }
}
