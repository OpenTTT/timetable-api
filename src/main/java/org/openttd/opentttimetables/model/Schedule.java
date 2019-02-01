package org.openttd.opentttimetables.model;

import com.google.common.base.Preconditions;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

/**
 * A single vehicle schedule, i.e. the times it arrives at every stations.
 *
 * Corresponds to a single departure for a single interval of a ScheduledDispatch
 */
public class Schedule {
    private List<ScheduledOrder> orders;

    public Schedule(List<ScheduledOrder> orders) {
        Preconditions.checkArgument(orders.size() >= 2, "Cannot build a schedule with empty orders"); // TODO test me
        this.orders = orders;
    }

    public List<ScheduledOrder> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    public LocalTime getStartTime() {
        return orders.get(0).getArrival();
    }

    /**
     * Gives the (implicit) order for the return trip of a given schedule.
     *
     * In OpenTTD, the return trip of an order list is neither explicitly entered, nor displayed.
     *
     * Since we are following the OpenTTD model pretty closely but need this information, this implicit order is
     * available the actual order list. The departure time of the returned order should not be used and will be set
     * with an arbitrary value.
     */
    public ScheduledOrder returnOrder() {
        Destination returnDestination = orders.get(0).getDestination();
        ScheduledOrder ultimateOrder = orders.get(orders.size() - 1);
        Integer ultimateTravelTime = ultimateOrder.getTimetabledOrder().getTravelingTime();
        LocalTime returnArrival = ultimateOrder.getDeparture().plusMinutes(ultimateTravelTime);
        return new ScheduledOrder(TimetabledOrder.returnOrder(returnDestination), returnArrival);
    }
}
