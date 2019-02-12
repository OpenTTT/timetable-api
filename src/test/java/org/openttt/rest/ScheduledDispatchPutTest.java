package org.openttt.rest;

import org.junit.Test;
import org.openttt.rest.dto.ScheduledDispatchDTO;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScheduledDispatchPutTest extends CreateMinimalTestDataControllerTest {
    @Test
    public void testPUTOfScheduledDispatchSucceeds() throws Exception {
        ScheduledDispatchDTO dto = generateScheduledDispatchDto();

        mvc.perform(put(urlForScheduledDispatch(0))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testPUTOfNonExistingScheduledDispatch404s() throws Exception {
        ScheduledDispatchDTO dto = generateScheduledDispatchDto();

        mvc.perform(put("/scheduled-dispatches/4711")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());

    }
    // Validation of ScheduledDispatchDto tested in ScheduledDispatchPostTest, if applicable.
    // We will test PUT-specific logic here
}
