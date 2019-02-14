package org.openttt.rest;

import org.junit.Test;
import org.openttt.rest.dto.StatsDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class StatsTest extends CreateMinimalTestDataControllerTest {
    @Test
    public void testStatsAreCorrectForMinimalTestData() throws Exception{
        StatsDTO stats = mapper.readValue(mvc.perform(get("/stats")).andReturn().getResponse().getContentAsString(), StatsDTO.class);

        assertThat(stats.getStations()).isEqualTo(destinations.size());
        assertThat(stats.getScheduledDispatches()).isEqualTo(dispatches.size());
        assertThat(stats.getTotalTimetables()).isEqualTo(timetables.size());

        long departures = dispatches.stream().mapToLong(d -> d.getDepartures().size()).sum();
        assertThat(departures).isEqualTo(2);
    }
}
