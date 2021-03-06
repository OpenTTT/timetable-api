package org.openttt.scheduling;

import org.junit.Test;
import org.openttt.TestData;
import org.openttt.model.Schedule;
import org.openttt.model.ScheduledDispatch;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ScheduleSupplierTest {
    private ScheduleSupplier givenSimpleSupplier() {
        return new ScheduleSupplier(TestData.SIMPLE_DISPATCH);
    }

    @Test
    public void testInitialSuppliedValueIsCorrect() {
        ScheduleSupplier supplier = givenSimpleSupplier();

        Schedule suppliedSchedule = supplier.get();

        assertThat(suppliedSchedule).isNotNull();
        assertThat(suppliedSchedule.getStartTime()).isEqualTo(LocalTime.of(0,0));
    }

    @Test
    public void testSuppliedValueWithinIntervalIsCorrect() {
        ScheduleSupplier supplier = givenSimpleSupplier();

        supplier.get(); // offsetIndex=0, offset=0min
        Schedule suppliedSchedule = supplier.get(); // offsetIndex=1, offset=30min

        assertThat(suppliedSchedule).isNotNull();
        assertThat(suppliedSchedule.getStartTime()).isEqualTo(LocalTime.of(0, 30));
    }

    @Test
    public void testSuppliedValueRollingOverIntoNextIntervalWithZeroOffset() {
        ScheduleSupplier supplier = givenSimpleSupplier();

        supplier.get(); // offsetIndex=0, offset=0min
        supplier.get(); // offsetIndex=1, offset=30min

        // offsetIndex=0, offset=0min, currentTime=0:00 (start time) + 60min (interval) = 1:00
        Schedule suppliedSchedule = supplier.get();

        assertThat(suppliedSchedule).isNotNull();
        assertThat(suppliedSchedule.getStartTime()).isEqualTo(LocalTime.of(1, 0));
    }

    @Test
    public void testRollingOverTwoIntervals() {
        ScheduleSupplier supplier = givenSimpleSupplier();

        supplier.get(); // currentTime=00:00
        supplier.get(); // currentTime=00:30
        supplier.get(); // currentTime=01:00 (1st rollover)
        supplier.get(); // currentTime=01:30

        // currentTime=02:00 (2nd rollover)
        Schedule suppliedSchedule = supplier.get();

        assertThat(suppliedSchedule).isNotNull();
        assertThat(suppliedSchedule.getStartTime()).isEqualTo(LocalTime.of(2, 0));
    }

    @Test
    public void testNonZeroFirstDepartureIsRespected() {
        ScheduledDispatch notSoSimpleDispatch = new ScheduledDispatch(60, List.of(15, 45), TestData.SIMPLE_ORDERS);
        ScheduleSupplier supplier = new ScheduleSupplier(notSoSimpleDispatch);

        Schedule firstSchedule = supplier.get();
        assertThat(firstSchedule.getStartTime()).isEqualTo(LocalTime.of(0, 15));

        Schedule secondSchedule = supplier.get();
        assertThat(secondSchedule.getStartTime()).isEqualTo(LocalTime.of(0, 45));

        Schedule rolledOverSchedule = supplier.get();
        assertThat(rolledOverSchedule.getStartTime()).isEqualTo(LocalTime.of(1, 15));
    }

    @Test
    public void testSupplierRespectsReturnOrder() {
        ScheduleSupplier supplier = new ScheduleSupplier(TestData.SIMPLE_DISPATCH, true);
        Schedule schedule = supplier.get();

        assertThat(schedule.getOrders()).hasSize(TestData.SIMPLE_ORDERS.getOrders().size() + 1);
    }
}
