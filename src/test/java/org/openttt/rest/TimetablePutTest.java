package org.openttt.rest;

import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TimetablePutTest extends CleanupControllerTest {
    @Test
    public void testCantPutNonExistingTimetable() throws Exception {
        var timetable = generateTimetableDto();
        timetable.setId(4711);

        mvc.perform(put("/timetables/4711")
                .content(mapper.writeValueAsString(timetable))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
