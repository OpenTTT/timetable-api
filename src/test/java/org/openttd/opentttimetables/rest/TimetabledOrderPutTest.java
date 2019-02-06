package org.openttd.opentttimetables.rest;

import org.junit.Test;
import org.openttd.opentttimetables.rest.dto.TimetableDTO;
import org.openttd.opentttimetables.rest.dto.TimetabledOrderDTO;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TimetabledOrderPutTest extends CreateMinimalTestDataControllerTest {
    @Test
    public void testPUTOfTimetabledOrderSucceeds() throws Exception {
        var dto = generateTimetabledOrderDto();
        putTimetabledOrder(dto).andExpect(status().isOk());

        TimetableDTO timetableDto = mapper.readValue(mvc.perform(get(urlForTimetable(0))).andReturn().getResponse().getContentAsString(),
                TimetableDTO.class);
        TimetabledOrderDTO updatedOrder = timetableDto.getOrders().get(1);
        assertThat(updatedOrder.getStayingTime()).isEqualTo(10);
        assertThat(updatedOrder.getTravelingTime()).isEqualTo(20);
    }

    @Test
    public void testPUTOfTimetabledOrderWithEmptyDestination400s() throws Exception {
        var dto = generateTimetabledOrderDto();
        dto.setDestination("");
        putTimetabledOrder(dto).andExpect(status().isBadRequest());
    }

    @Test
    public void testPUTOfTimetabledOrderWithExcessivelyHighStayingTime400s() throws Exception {
        var dto = generateTimetabledOrderDto();
        dto.setStayingTime(Integer.MAX_VALUE);

        putTimetabledOrder(dto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPUTOfTimetabledOrderWithExcessivelyLowStayingTime400s() throws Exception {
        var dto = generateTimetabledOrderDto();
        dto.setStayingTime(-1);

        putTimetabledOrder(dto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPUTOfTimetabledOrderWithExcessivelyHighTravelingTime400s() throws Exception {
        var dto = generateTimetabledOrderDto();
        dto.setTravelingTime(Integer.MAX_VALUE);

        putTimetabledOrder(dto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPUTOfTimetabledOrderWithExcessivelyLowTravelingTime400s() throws Exception {
        var dto = generateTimetabledOrderDto();
        dto.setTravelingTime(-1);

        putTimetabledOrder(dto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPUTOfTimetabledOrderWithModifiedDestination400s() throws Exception {
        var dto = generateTimetabledOrderDto();
        dto.setDestination("Wien");

        putTimetabledOrder(dto)
                .andExpect(status().isBadRequest());
    }

    private ResultActions putTimetabledOrder(TimetabledOrderDTO dto) throws Exception {
        dto.setId(timetables.get(0).getOrders().get(1).getId());
        return mvc.perform(put(urlForTimetabledOrder(0, 1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)));
    }

}
