package org.openttd.opentttimetables.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openttd.opentttimetables.OpenTTTimetablesApplication;
import org.openttd.opentttimetables.rest.dto.TimetableDTO;
import org.openttd.opentttimetables.rest.dto.TimetabledOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = OpenTTTimetablesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TimetableControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testCreationOfTimetableSucceeds() throws Exception {
        mvc.perform(postTimetable(timetableDto()))
                .andExpect(status().isCreated());
    }

    @Test
    public void testTimetableWithNoNameRejected() throws Exception {
        var dto = timetableDto();
        dto.setName("");

        mvc.perform(postTimetable(dto))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testTimetableWithInvalidOrdersRejected() throws Exception {
        var dto = timetableDto();
        dto.setOrders(List.of());

        mvc.perform(postTimetable(dto))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateOfTimetabledOrderSucceeds() throws Exception {
        var dto = timetabledOrderDto();
        putTimetabledOrder(dto).andExpect(status().isOk());

        TimetableDTO timetableDto = mapper.readValue(mvc.perform(get("/timetable/1")).andReturn().getResponse().getContentAsString(), TimetableDTO.class);
        TimetabledOrderDTO updatedOrder = timetableDto.getOrders().get(1);
        assertThat(updatedOrder.getStayingTime()).isEqualTo(10);
        assertThat(updatedOrder.getTravelingTime()).isEqualTo(20);
    }

    @Test
    public void testEmptyDestinationRejected() throws Exception {
        var dto = timetabledOrderDto();
        dto.setDestination("");
        putTimetabledOrder(dto).andExpect(status().isBadRequest());
    }

    @Test
    public void testTooHighStayingTimeRejected() throws Exception {
        var dto = timetabledOrderDto();
        dto.setStayingTime(Integer.MAX_VALUE);

        putTimetabledOrder(dto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testTooLowStayingTimeRejected() throws Exception {
        var dto = timetabledOrderDto();
        dto.setStayingTime(-1);

        putTimetabledOrder(dto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testTooHighTravelingTime() throws Exception {
        var dto = timetabledOrderDto();
        dto.setTravelingTime(Integer.MAX_VALUE);

        putTimetabledOrder(dto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testTooLowTravelingTime() throws Exception {
        var dto = timetabledOrderDto();
        dto.setTravelingTime(-1);

        putTimetabledOrder(dto)
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testCannotChangeDestinationOnUpdate() throws Exception {
        var dto = timetabledOrderDto();
        dto.setDestination("Wien");

        putTimetabledOrder(dto)
                .andExpect(status().isBadRequest());
    }

    private MockHttpServletRequestBuilder postTimetable(TimetableDTO dto) throws JsonProcessingException {
        return post("/timetable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto));
    }

    private ResultActions putTimetabledOrder(TimetabledOrderDTO dto) throws Exception {
        return mvc.perform(put("/timetable/1/order/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)));
    }

    private static TimetableDTO timetableDto() {
        return new TimetableDTO(null, "RE 1", List.of(
                new TimetabledOrderDTO(null, "Rosenheim", 10, 10, false),
                new TimetabledOrderDTO(null, "Rheinstetten Bahnhof", 10, 10, false)
        ));
    }

    private static TimetabledOrderDTO timetabledOrderDto() {
        return new TimetabledOrderDTO(2, "Rheinstetten Bahnhof", 10, 20, false);
    }
}
