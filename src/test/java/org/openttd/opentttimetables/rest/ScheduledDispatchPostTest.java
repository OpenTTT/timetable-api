package org.openttd.opentttimetables.rest;

import org.junit.Test;
import org.openttd.opentttimetables.rest.dto.ScheduledDispatchDTO;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScheduledDispatchPostTest extends CreateMinimalTestDataControllerTest {
    @Test
    public void testPOSTOfScheduledDispatchSucceeds() throws Exception {
        ScheduledDispatchDTO dto = generateScheduledDispatchDto();

        postDispatch(dto).andExpect(status().isOk());
    }

    @Test
    public void testPOSTOfScheduledDispatchWithTooFewDepartures400s() throws Exception {
        ScheduledDispatchDTO dto = generateScheduledDispatchDto();
        dto.setDepartures(List.of());

        postDispatch(dto).andExpect(status().isBadRequest());
    }

    @Test
    public void testPOSTOfScheduledDispatchWithTooLowInterval400s() throws Exception {
        ScheduledDispatchDTO dto = generateScheduledDispatchDto();
        dto.setIntervalInMinutes(0);

        postDispatch(dto).andExpect(status().isBadRequest());
    }

    @Test
    public void testPOSTOfScheduledDispatchWithTooHighInterval400s() throws Exception {
        ScheduledDispatchDTO dto = generateScheduledDispatchDto();
        dto.setIntervalInMinutes(4711);

        postDispatch(dto).andExpect(status().isBadRequest());
    }

    private ResultActions postDispatch(ScheduledDispatchDTO dto) throws Exception {
        return mvc.perform(post("/scheduled-dispatches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)));
    }
}
