package org.openttd.opentttimetables.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openttd.opentttimetables.OpenTTTimetablesApplication;
import org.openttd.opentttimetables.repo.TimetableRepo;
import org.openttd.opentttimetables.repo.TimetabledOrderRepo;
import org.openttd.opentttimetables.rest.dto.TimetableDTO;
import org.openttd.opentttimetables.rest.dto.TimetabledOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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

    @Autowired
    private TimetabledOrderRepo orderRepo;

    @Autowired
    private TimetableRepo timetableRepo;

    @Test
    public void testPOSTOfValidTimetableSucceeds() throws Exception {
        MvcResult result = mvc.perform(postTimetable(timetableDto()))
                .andExpect(status().isCreated())
                .andReturn();

        timetableRepo.deleteById(readTimetable(result).getId());
    }

    @Test
    public void testPOSTOfTimetableWithNoName400s() throws Exception {
        var dto = timetableDto();
        dto.setName("");

        mvc.perform(postTimetable(dto))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPOSTWithInvalidOrders400s() throws Exception {
        var dto = timetableDto();
        dto.setOrders(List.of());

        mvc.perform(postTimetable(dto))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPUTOfTimetabledOrderSucceeds() throws Exception {
        var dto = timetabledOrderDto();
        putTimetabledOrder(dto).andExpect(status().isOk());

        TimetableDTO timetableDto = mapper.readValue(mvc.perform(get("/timetable/1")).andReturn().getResponse().getContentAsString(), TimetableDTO.class);
        TimetabledOrderDTO updatedOrder = timetableDto.getOrders().get(1);
        assertThat(updatedOrder.getStayingTime()).isEqualTo(10);
        assertThat(updatedOrder.getTravelingTime()).isEqualTo(20);

        var resetDto = timetabledOrderDto();
        resetDto.setStayingTime(3);
        resetDto.setTravelingTime(8);
        putTimetabledOrder(resetDto);
    }

    @Test
    public void testPUTOfTimetabledOrderWithEmptyDestination400s() throws Exception {
        var dto = timetabledOrderDto();
        dto.setDestination("");
        putTimetabledOrder(dto).andExpect(status().isBadRequest());
    }

    @Test
    public void testPUTOfTimetabledOrderWithExcessivelyHighStayingTime400s() throws Exception {
        var dto = timetabledOrderDto();
        dto.setStayingTime(Integer.MAX_VALUE);

        putTimetabledOrder(dto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPUTOfTimetabledOrderWithExcessivelyLowStayingTime400s() throws Exception {
        var dto = timetabledOrderDto();
        dto.setStayingTime(-1);

        putTimetabledOrder(dto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPUTOfTimetabledOrderWithExcessivelyHighTravelingTime400s() throws Exception {
        var dto = timetabledOrderDto();
        dto.setTravelingTime(Integer.MAX_VALUE);

        putTimetabledOrder(dto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPUTOfTimetabledOrderWithExcessivelyLowTravelingTime400s() throws Exception {
        var dto = timetabledOrderDto();
        dto.setTravelingTime(-1);

        putTimetabledOrder(dto)
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testPUTOfTimetabledOrderWithModifiedDestination400s() throws Exception {
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

    private TimetableDTO readTimetable(MvcResult result) throws Exception {
        return mapper.readValue(result.getResponse().getContentAsString(), TimetableDTO.class);
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
