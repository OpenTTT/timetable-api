package org.openttd.opentttimetables.rest;

import org.junit.Test;
import org.openttd.opentttimetables.rest.dto.ScheduledDispatchDTO;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScheduledDispatchPostTest extends CreateMinimalTestDataControllerTest {
    @Test
    public void testPOSTOfScheduledDispatch() throws Exception {
        ScheduledDispatchDTO dto = new ScheduledDispatchDTO();
        dto.setIntervalInMinutes(60);
        dto.setDepartures(List.of(0, 30));
        dto.setTimetableId(this.timetables.get(0).getId());

        mvc.perform(post("/scheduled-dispatch")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(dto)))
            .andExpect(status().isOk());
    }

    // No validation done at the moment, so we don't need to check further
}
