package org.openttt.rest;

import org.junit.Test;
import org.openttt.rest.dto.DestinationDTO;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DestinationGetTest extends CreateMinimalTestDataControllerTest {
    @Test
    public void testGETOfAllDestinationsSucceeds() throws Exception {
        MvcResult result = mvc.perform(get("/destinations/"))
                .andExpect(status().isOk())
                .andReturn();
        List<DestinationDTO> dtos = readListOfDestinations(result);

        assertThat(dtos.stream().map(DestinationDTO::getName)).containsExactlyInAnyOrder(
            "Rosenheim",
            "Rheinstetten Bahnhof"
        );
    }
}
