package org.openttd.opentttimetables.timetabling;

import lombok.AllArgsConstructor;
import org.openttd.opentttimetables.model.ScheduledDispatch;
import org.openttd.opentttimetables.model.TimetabledOrder;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class SingleDispatchTimetable {
    private LocalTime currentTime;
    private ScheduledDispatch schedule;

    // TODO: This is just a proof of concept. The finished product will probably yield a Stream<TimetableEntries> or similar
    public String nextTimetableAsText() {
        return buildStringForOrders(schedule.getOrders());

    }

    private String buildStringForOrders(List<TimetabledOrder> orders) {
        StringBuilder sb = new StringBuilder("ARRIVAL\t\tDEPARTURE\t\tSTOP\n");
        boolean isInitialStop = true;
        for (TimetabledOrder order : schedule.getOrders()) {
            String currentStation = order.getOrder().getDestination().getName();
            LocalTime departureTime = currentTime.plusMinutes(order.getStayingTime());

            if (isInitialStop) {
                sb.append("--:--");
                isInitialStop =false;
            } else {
                sb.append(currentTime);
            }
            sb.append("\t\t").append(departureTime).append("\t\t").append(currentStation).append("\n");

            currentTime = departureTime.plusMinutes(order.getTravelingTime());
        }
        String lastStation = orders.get(0).getOrder().getDestination().getName();
        sb.append(currentTime).append("\t\t").append("--:--").append("\t\t").append(lastStation);

        return sb.toString();
    }
}
