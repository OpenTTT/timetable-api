package org.openttt.model;

import org.junit.Test;
import org.openttt.TestData;

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
}
