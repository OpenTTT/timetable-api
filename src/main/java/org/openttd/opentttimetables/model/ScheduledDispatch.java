package org.openttd.opentttimetables.model;

import lombok.Data;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Direct represenation of the "Scheduled Dispatch" dialogue in OpenTTD.
 *
 * Contains methods to generate single schedules for the dispatch based on its timetabled orders.
 */
@Data
public class ScheduledDispatch {
    private List<LocalTime> departures;
    private Integer intervalInMinutes;
    private List<TimetabledOrder> orders;


    public ScheduledDispatch(List<LocalTime> departures, Integer intervalInMinutes, List<TimetabledOrder> orders) {
        this.departures = departures;
        this.intervalInMinutes = intervalInMinutes;
        this.orders = orders;
    }

    // TODO: maybe a stream?
    public List<Schedule> schedulesForInterval() {
        return departures.stream()
                .map(this::scheduleOrdersForDeparture)
                .collect(Collectors.toList());
    }

    private Schedule scheduleOrdersForDeparture(LocalTime departure) {
        LocalTime currentTime = LocalTime.from(departure);
        List<ScheduledOrder> scheduledOrders = new ArrayList<>(orders.size());
        for (TimetabledOrder order : orders) {
            ScheduledOrder scheduledOrder = new ScheduledOrder(order, currentTime);
            currentTime = scheduledOrder.getDeparture().plusMinutes(order.getTravelingTime());
            scheduledOrders.add(scheduledOrder);
        }

        return new Schedule(scheduledOrders);
    }
}
