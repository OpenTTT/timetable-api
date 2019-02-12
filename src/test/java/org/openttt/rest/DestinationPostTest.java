package org.openttt.rest;

import org.junit.Test;
import org.openttt.rest.dto.DestinationDTO;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DestinationPostTest extends CreateMinimalTestDataControllerTest {
    @Test
    public void testPOSTOfValidDestinationSucceeds() throws Exception {
        DestinationDTO destination = new DestinationDTO();
        destination.setName("Wien Ottakring");
        destination.setDestinationType("STATION");
        String destinationJson = mapper.writeValueAsString(destination);

        MvcResult postResult = mvc.perform(post("/destinations/")
                .content(destinationJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        DestinationDTO postedDestination = readDestination(postResult);

        assertThat(postedDestination).isEqualTo(destination);

        List<String> allDestinations = readListOfDestinations(mvc.perform(get("/destinations/")).andReturn())
                .stream()
                .map(DestinationDTO::getName)
                .collect(Collectors.toList());

        assertThat(allDestinations).hasSize(3);
        assertThat(allDestinations).contains("Wien Ottakring");
    }

    @Test
    public void testPOSTOfDestinationWithMissingType400s() throws Exception {
        DestinationDTO destination = new DestinationDTO();
        destination.setName("Wien Ottakring");
        String newStationJson = mapper.writeValueAsString(destination);
        mvc.perform(post("/destinations/")
                .content(newStationJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPOSTOfDestinationWithMissingName400s() throws Exception {
        DestinationDTO destination = new DestinationDTO();
        destination.setDestinationType("STATION");
        String newStationJson = mapper.writeValueAsString(destination);
        mvc.perform(post("/destinations/")
                .content(newStationJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
