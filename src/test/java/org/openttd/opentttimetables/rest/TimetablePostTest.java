package org.openttd.opentttimetables.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openttd.opentttimetables.OpenTTTimetablesApplication;
import org.openttd.opentttimetables.rest.dto.TimetableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = OpenTTTimetablesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TimetablePostTest extends CleanupControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testPOSTOfValidTimetableSucceeds() throws Exception {
        MvcResult result = mvc.perform(postTimetable(generateTimetableDto()))
                .andExpect(status().isCreated())
                .andReturn();

        timetableRepo.deleteById(readTimetable(mapper, result).getId());
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
        return post("/timetable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto));
    }


}
