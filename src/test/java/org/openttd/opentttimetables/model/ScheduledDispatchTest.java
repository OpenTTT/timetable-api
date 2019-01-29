package org.openttd.opentttimetables.model;

import org.junit.Test;
import org.openttd.opentttimetables.TestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ScheduledDispatchTest {
    private final static List<Integer> VALID_DEPARTURES = List.of(15, 45);
    @Test
    public void cantCreateZeroInterval() {
        assertThatThrownBy(() -> new ScheduledDispatch(0, VALID_DEPARTURES, TestData.VIRM4_MP_ORDERS))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("interval");
    }

    @Test
    public void cantCreateWithoutDepartures() {
        assertThatThrownBy(() -> new ScheduledDispatch(60, List.of(), TestData.VIRM4_MP_ORDERS))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("departures");
    }

    @Test
    public void cantCreateWithDeparturesGreaterThanInterval() {
        assertThatThrownBy(() -> new ScheduledDispatch(60, List.of(45, 60), TestData.VIRM4_MP_ORDERS))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("departure");
    }

    @Test
    public void cantCreateWithoutOrders() {
        assertThatThrownBy(() -> new ScheduledDispatch(60, VALID_DEPARTURES, List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("two orders");
    }

    @Test
    public void cantCreateWithSingularOrder() {
        assertThatThrownBy(() -> new ScheduledDispatch(60, VALID_DEPARTURES, TestData.VIRM4_MP_ORDERS.subList(0, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("two orders");
    }
}
