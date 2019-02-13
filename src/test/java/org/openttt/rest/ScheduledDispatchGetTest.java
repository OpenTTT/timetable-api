package org.openttt.rest;

import org.junit.Test;
import org.openttt.rest.dto.*;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests the get methods for the {@link ScheduledDispatchController}.
 *
 * It really only tests the structure of what comes out is valid, since the scheduling code is
 * tested in dedicated tests elsewhere.
 */
public class ScheduledDispatchGetTest extends CreateMinimalTestDataControllerTest {
    @Test
    public void testGETOfAllSchedulesSucceeds() throws Exception {
        List<ScheduledDispatchDTO> dispatches = readScheduledDispatchList(mvc.perform(get("/scheduled-dispatches")).andReturn());
        assertThat(dispatches).hasSize(1);
    }

    @Test
    public void testGETOfSpecificScheduledDispatchSucceeds() throws Exception {
        ScheduledDispatchDTO dispatch = readScheduledDispatch(mvc.perform(get(urlForScheduledDispatch(0))).andReturn());
        assertThat(dispatch.getIntervalInMinutes()).isEqualTo(dispatches.get(0).getIntervalInMinutes());
        assertThat(dispatch.getDepartures()).isEqualTo(dispatches.get(0).getDepartures());
        assertThat(dispatch.getTimetableId()).isEqualTo(timetables.get(0).getId());
        assertThat(dispatch.getTimetable()).isNotBlank();
    }

    @Test
    public void testGETOfNonExistentScheduledDispatch404s() throws Exception {
        mvc.perform(get("/scheduled-dispatches/4711"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGETOfDeparturesSucceeds() throws Exception {
        List<ScheduleDTO> schedule = readScheduledDispatchDepartures(mvc.perform(get(urlForScheduledDispatchDepartures(0))).andReturn());
        assertThat(schedule).hasSize(5); // This value is hardcoded, lol!

        ScheduledOrderDTO someOrder = schedule.get(0).getOrders().get(0);
        assertThat(someOrder.getDestination()).isNotBlank();
        assertThat(someOrder.getArrival()).isNotBlank();
        assertThat(someOrder.getDeparture()).isNotBlank();
    }

    @Test
    public void testGETOfDeparturesByStationSucceeds() throws Exception {
        List<SchedulesByStationDTO> schedules = readScheduledDispatchesByStation(mvc.perform(get(urlForScheduledDispatchDeparturesByStation(0))).andReturn());

        assertThat(schedules).hasSameSizeAs(this.destinations);

        SchedulesByStationDTO firstStationDepartures = schedules.get(0);
        assertThat(firstStationDepartures.getStation()).isNotBlank();
        assertThat(firstStationDepartures.getReturnTrip()).isFalse();

        List<ScheduleDepartureDTO> orders = firstStationDepartures.getDepartures();
        assertThat(orders).hasSize(5); // Default value
        assertThat(orders.get(0).getArrival()).isNotBlank();
        assertThat(orders.get(0).getDeparture()).isNotBlank();
    }

    @Test
    public void testGETOfDeparturesByStationRespectsDepartureCount() throws Exception {
        List<ScheduleDepartureDTO> departures = getDepartures(1);
        assertThat(departures).hasSize(1);
    }

    @Test
    public void testGETOfDeparturesByStationRespectsReturnOrder() throws Exception {
        List<SchedulesByStationDTO> schedules = readScheduledDispatchesByStation(
                mvc.perform(get(urlForScheduledDispatchDeparturesByStation(0))
                        .param("withReturnOrder", "true")).andReturn());

        assertThat(schedules).hasSize(timetables.get(0).getOrders().size() + 1);

        SchedulesByStationDTO lastDestination = schedules.get(schedules.size() - 1);
        assertThat(lastDestination.getReturnTrip()).isTrue();
        assertThat(lastDestination.getStation()).isEqualTo(destinations.get(0).getName());
    }
    
    private List<ScheduleDepartureDTO> getDepartures(Integer numberOfDepartures) throws Exception {
        MvcResult mvcResult = requestDeparturesByStation(numberOfDepartures).andReturn();
        return readScheduledDispatchesByStation(mvcResult).get(0).getDepartures();
    }

    private ResultActions requestDeparturesByStation(Integer numberOfDepartures) throws Exception {
        MockHttpServletRequestBuilder getRequest = get(urlForScheduledDispatchDeparturesByStation(0))
                .param("numberOfDepartures", numberOfDepartures.toString());
        return mvc.perform(getRequest);
    }
}
