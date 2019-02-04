package org.openttd.opentttimetables.rest;

import com.fasterxml.jackson.core.type.TypeReference;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = OpenTTTimetablesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DestinationControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testGETOfAllDestinationsSucceeds() throws Exception {
        MvcResult result = mvc.perform(get("/destinations/"))
                .andExpect(status().isOk())
                .andReturn();

        List<DestinationDTO> dtos = readListOfDestinations(result);
        assertThat(dtos).hasSize(5);
        assertThat(dtos.stream().map(DestinationDTO::getName)).containsExactlyInAnyOrder(
                "Rosenheim",
                "Rheinstetten Bahnhof",
                "R-Crailsheim",
                "Obernburg",
                "Veringenstadt"
        );
    }

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

        List<DestinationDTO> allDestinations = readListOfDestinations(mvc.perform(get("/destinations/")).andReturn());
        assertThat(allDestinations).hasSize(6);
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

    private DestinationDTO readDestination(MvcResult result) throws java.io.IOException {
        return mapper.readValue(result.getResponse().getContentAsString(), DestinationDTO.class);
    }

    private List<DestinationDTO> readListOfDestinations(MvcResult result) throws java.io.IOException {
        return mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<DestinationDTO>>() {
        });
    }
}
