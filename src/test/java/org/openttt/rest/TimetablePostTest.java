package org.openttt.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.openttt.rest.dto.TimetableDTO;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TimetablePostTest extends CleanupControllerTest {
    @Test
    public void testPOSTOfValidTimetableSucceeds() throws Exception {
        MvcResult result = mvc.perform(postTimetable(generateTimetableDto()))
                .andExpect(status().isCreated())
                .andReturn();

        timetableRepo.deleteById(readTimetable(result).getId());
    }

    @Test
    public void testPOSTOfTimetableWithNoName400s() throws Exception {
        var dto = generateTimetableDto();
        dto.setName("");

        mvc.perform(postTimetable(dto))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPOSTWithInvalidOrders400s() throws Exception {
        var dto = generateTimetableDto();
        dto.setOrders(List.of());

        mvc.perform(postTimetable(dto))
                .andExpect(status().isBadRequest());
    }

    private MockHttpServletRequestBuilder postTimetable(TimetableDTO dto) throws JsonProcessingException {
        return post("/timetables")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto));
    }


}
