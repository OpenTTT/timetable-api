package org.openttd.opentttimetables.model;

import com.google.common.base.Preconditions;

import java.util.List;

/**
 * Direct represenation of the "Scheduled Dispatch" dialogue in OpenTTD.
 *
 * Contains methods to generate single schedules for the dispatch based on its timetabled orders.
 */
public class ScheduledDispatch {
    /**
     * The interval after which the dispatching continues.
     */
    private Integer intervalInMinutes;
    /**
     * The departures within the interval, as offset from the start time in minutes.
     */
    private List<Integer> departures;
    /**
     * The orders this scheduled dispatch should encompass.
     */
    private List<TimetabledOrder> orders;

    public ScheduledDispatch(Integer intervalInMinutes, List<Integer> departures, List<TimetabledOrder> orders) {
        Preconditions.checkArgument(intervalInMinutes > 0, "interval must be larger than zero.");
        Preconditions.checkArgument(departures.size() > 0, "departures may not be empty");
        Preconditions.checkArgument(departures.stream().noneMatch(i -> i >= intervalInMinutes),
                "no departure may be later than the interval");
        Preconditions.checkArgument(orders.size() >= 2, "order list must contain two orders");

        this.intervalInMinutes = intervalInMinutes;
        this.departures = departures;
        this.orders = orders;
    }

    public List<TimetabledOrder> getOrders() {
        return orders;
    }

    public List<Integer> getDepartures() {
        return departures;
    }

    public Integer getIntervalInMinutes() {
        return intervalInMinutes;
    }
}
