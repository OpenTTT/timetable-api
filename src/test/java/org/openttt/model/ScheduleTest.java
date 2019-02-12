package org.openttt.model;

import org.junit.Test;
import org.openttt.TestData;
import org.openttt.scheduling.DepartureScheduler;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ScheduleTest {
    private Schedule givenSimpleSchedule() {
        return new DepartureScheduler(LocalTime.MIDNIGHT, TestData.SIMPLE_ORDERS).schedule();
    }

    @Test
    public void testCorrectStartTime() {
        Schedule schedule = givenSimpleSchedule();
        assertThat(schedule.getStartTime()).isEqualTo(LocalTime.MIDNIGHT);
    }

    @Test
    public void testCorrectReturnOrder() {
        Schedule schedule = givenSimpleSchedule();
        ScheduledOrder returnOrder = schedule.returnOrder();

        assertThat(returnOrder).isNotNull();
        assertThat(returnOrder.getArrival()).isEqualTo(LocalTime.of(0, 20));
        assertThat(returnOrder.getDestination()).isEqualTo(TestData.VIRM4_MP_DESTINATIONS.get("Rheinstetten"));
    }
}
