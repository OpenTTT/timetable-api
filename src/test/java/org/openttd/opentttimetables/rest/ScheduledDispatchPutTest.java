package org.openttd.opentttimetables.rest;

import org.junit.Test;
import org.openttd.opentttimetables.rest.dto.ScheduledDispatchDTO;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScheduledDispatchPutTest extends CreateMinimalTestDataControllerTest {
    @Test
    public void testUpdateOfScheduledDispatchSucceeds() throws Exception {
        ScheduledDispatchDTO dto = new ScheduledDispatchDTO();
        dto.setId(dispatches.get(0).getId());
        dto.setTimetableId(timetables.get(0).getId());
        dto.setIntervalInMinutes(90);
        dto.setDepartures(List.of(15, 45));

        mvc.perform(put(urlForScheduledDispatch(0))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    // Validation of ScheduledDispatchDto tested in ScheduledDispatchPostTest, if applicable.
    // We will test PUT-specific logic here
}
