package org.openttt.scheduling;

import org.junit.Test;
import org.openttt.TestData;
import org.openttt.model.Schedule;
import org.openttt.model.ScheduledOrder;

import java.time.LocalTime;
import java.util.List;

import static java.time.LocalTime.MIDNIGHT;
import static org.assertj.core.api.Assertions.assertThat;

public class DepartureSchedulerTest {
    @Test
    public void testStructuralIntegrityOfGeneratedSchedule() {
        Schedule schedule = givenSimpleGeneratedSchedule();

        assertThat(schedule).isNotNull();
        assertThat(schedule.getOrders()).hasSize(TestData.SIMPLE_ORDERS.getOrders().size());
        assertThat(schedule.getStartTime()).isEqualTo(MIDNIGHT);
    }

    @Test
    public void testOrdersOfGeneratedSchedule() {
        Schedule schedule = givenSimpleGeneratedSchedule();

        ScheduledOrder firstOrder = schedule.getOrders().get(0);
        assertThat(firstOrder.getDestination()).isEqualTo(TestData.VIRM4_MP_DESTINATIONS.get("Rheinstetten"));
        assertThat(firstOrder.getArrival()).isEqualTo(MIDNIGHT);
        assertThat(firstOrder.getDeparture()).isEqualTo(MIDNIGHT.plusMinutes(1));

        ScheduledOrder secondOrder = schedule.getOrders().get(1);
        assertThat(secondOrder.getDestination()).isEqualTo(TestData.VIRM4_MP_DESTINATIONS.get("Crailsheim"));
        assertThat(secondOrder.getArrival()).isEqualTo(LocalTime.of(0, 10));
        assertThat(secondOrder.getDeparture()).isEqualTo(LocalTime.of(0, 11));
    }

    @Test
    public void testReturnTripIsGeneratedIfRequested() {
        Schedule schedule = new DepartureScheduler(MIDNIGHT, TestData.SIMPLE_ORDERS, true)
                .schedule();

        List<ScheduledOrder> scheduledOrders = schedule.getOrders();
        assertThat(scheduledOrders).hasSize(givenSimpleGeneratedSchedule().getOrders().size() + 1);

        ScheduledOrder returnOrder = scheduledOrders.get(scheduledOrders.size() - 1);
        assertThat(returnOrder.getDestination().getName()).isEqualTo("Rheinstetten Bahnhof");
        assertThat(returnOrder.getArrival()).isEqualTo("00:20");
    }

    private Schedule givenSimpleGeneratedSchedule() {
        return new DepartureScheduler(MIDNIGHT, TestData.SIMPLE_ORDERS)
                .schedule();
    }
}
