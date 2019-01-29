package org.openttd.opentttimetables.scheduling;

import org.junit.Test;
import org.openttd.opentttimetables.TestData;
import org.openttd.opentttimetables.model.Schedule;
import org.openttd.opentttimetables.model.ScheduledOrder;

import java.time.LocalTime;

import static java.time.LocalTime.MIDNIGHT;
import static org.assertj.core.api.Assertions.assertThat;

public class DepartureSchedulerTest {
    @Test
    public void testStructuralIntegrityOfGeneratedSchedule() {
        Schedule schedule = givenSimpleGeneratedSchedule();

        assertThat(schedule).isNotNull();
        assertThat(schedule.orders()).hasSize(TestData.SIMPLE_ORDERS.size());
        assertThat(schedule.getStartTime()).isEqualTo(MIDNIGHT);
    }

    @Test
    public void testOrdersOfGeneratedSchedule() {
        Schedule schedule = givenSimpleGeneratedSchedule();

        ScheduledOrder firstOrder = schedule.orders().get(0);
        assertThat(firstOrder.getDestination()).isEqualTo(TestData.VIRM4_MP_DESTINATIONS.get("Rheinstetten"));
        assertThat(firstOrder.getArrival()).isEqualTo(MIDNIGHT);
        assertThat(firstOrder.getDeparture()).isEqualTo(MIDNIGHT.plusMinutes(1));

        ScheduledOrder secondOrder = schedule.orders().get(1);
        assertThat(secondOrder.getDestination()).isEqualTo(TestData.VIRM4_MP_DESTINATIONS.get("Crailsheim"));
        assertThat(secondOrder.getArrival()).isEqualTo(LocalTime.of(0, 10));
        assertThat(secondOrder.getDeparture()).isEqualTo(LocalTime.of(0, 11));
    }

    private Schedule givenSimpleGeneratedSchedule() {
        return new DepartureScheduler(MIDNIGHT, TestData.SIMPLE_ORDERS)
                .schedule();
    }
}