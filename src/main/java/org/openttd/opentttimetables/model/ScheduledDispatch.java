package org.openttd.opentttimetables.model;

import lombok.Data;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public Stream<Schedule> schedules() {
        return Stream.generate(new Generator());
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

    // TODO this complete class is utterly disgusting. I want to kill myself.
    private class Generator implements Supplier<Schedule> {
        private LocalTime baseTime = departures.get(0);
        private LocalTime currentTime = departures.get(0);
        private int offsetIndex = 0;
        private List<Integer> offsets = departures.stream().map(d -> (int)ChronoUnit.MINUTES.between(currentTime, d)).collect(Collectors.toList());

        @Override
        public Schedule get() {
            Schedule schedule = scheduleOrdersForDeparture(currentTime);
            if (++offsetIndex < offsets.size()) {
                currentTime = baseTime.plusMinutes(offsets.get(offsetIndex));
            } else {
                baseTime = baseTime.plusMinutes(intervalInMinutes);
                offsetIndex = 0;
                currentTime = baseTime.plusMinutes(offsets.get(offsetIndex));
            }
            return schedule;
        }
    }
}
