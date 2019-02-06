package org.openttd.opentttimetables.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openttd.opentttimetables.OpenTTTimetablesApplication;
import org.openttd.opentttimetables.rest.dto.DestinationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = OpenTTTimetablesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DestinationPostTest extends CreateMinimalTestDataControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

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
        DestinationDTO postedDestination = readDestination(mapper, postResult);

        assertThat(postedDestination).isEqualTo(destination);

        List<String> allDestinations = readListOfDestinations(mapper, mvc.perform(get("/destinations/")).andReturn())
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
        mvc.perform(post("/destination/")
                .content(newStationJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPOSTOfDestinationWithMissingName400s() throws Exception {
        DestinationDTO destination = new DestinationDTO();
        destination.setDestinationType("STATION");
        String newStationJson = mapper.writeValueAsString(destination);
        mvc.perform(post("/destination/")
                .content(newStationJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
