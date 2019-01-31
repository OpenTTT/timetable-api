package org.openttd.opentttimetables.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openttd.opentttimetables.OpenTTTimetablesApplication;
import org.openttd.opentttimetables.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = OpenTTTimetablesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DestinationControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void testGet() throws Exception {
        mvc.perform(get("/destinations/"))
                .andExpect(status().isOk())
                .andExpect(content().json(Strings.get("json/destinations/list.json")));
    }

    @Test
    public void testCreationOfNewStation() throws Exception {
        // Create new valid station
        String newStationJson = Strings.get("json/destinations/valid-station.json");
        mvc.perform(post("/destination/")
                .content(newStationJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(newStationJson)); // Should return representation as per HTTP POST contract

        mvc.perform(get("/destinations/"))
                .andExpect(content().json(Strings.get("json/destinations/list-with-new-station.json")));
    }

    @Test
    public void testInvalidStationTypeMissing() throws Exception {
        String newStationJson = Strings.get("json/destinations/invalid-station-type-missing.json");
        mvc.perform(post("/destination/").content(newStationJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidStationNameEmpty() throws Exception {
        String newStationJson = Strings.get("json/destinations/invalid-station-name-empty.json");
        mvc.perform(post("/destination/").content(newStationJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
