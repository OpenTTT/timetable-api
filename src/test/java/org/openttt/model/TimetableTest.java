package org.openttt.model;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.openttt.TestData.SINGLE_ORDER;

public class TimetableTest {
    @Test
    public void testCantCreateTimetableWithEmptyOrders() {
        assertThatThrownBy(() -> new Timetable("invalid", List.of()))
                .hasMessageContaining("two getOrders");
    }

    @Test
    public void testCantCreateTimetableWithOneOrder() {
        assertThatThrownBy(() -> new Timetable("invalid", List.of(SINGLE_ORDER)))
                .hasMessageContaining("two getOrders");
    }
}
