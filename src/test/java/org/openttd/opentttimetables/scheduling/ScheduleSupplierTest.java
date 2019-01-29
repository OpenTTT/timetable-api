package org.openttd.opentttimetables.scheduling;

import org.junit.Test;
import org.openttd.opentttimetables.TestData;
import org.openttd.opentttimetables.model.Schedule;

import java.time.LocalTime;

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
}
