package org.openttd.opentttimetables.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.openttd.opentttimetables.OpenTTTimetablesApplication;
import org.openttd.opentttimetables.rest.dto.DestinationDTO;
import org.openttd.opentttimetables.rest.dto.TimetableDTO;
import org.openttd.opentttimetables.rest.dto.TimetabledOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = OpenTTTimetablesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
abstract class ControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    DestinationDTO readDestination(MvcResult result) throws java.io.IOException {
        return mapper.readValue(result.getResponse().getContentAsString(), DestinationDTO.class);
    }

    List<DestinationDTO> readListOfDestinations(MvcResult result) throws java.io.IOException {
        return mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<DestinationDTO>>() {
        });
    }

    TimetableDTO readTimetable(MvcResult result) throws Exception {
        return mapper.readValue(result.getResponse().getContentAsString(), TimetableDTO.class);
    }

    List<TimetableDTO> readListOfTimetables(MvcResult result) throws Exception {
        return mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<TimetableDTO>>(){
        });
    }

    static TimetableDTO generateTimetableDto() {
        return new TimetableDTO(null, "RE 1", List.of(
                new TimetabledOrderDTO(null, "Rosenheim", 10, 10, false),
                new TimetabledOrderDTO(null, "Rheinstetten Bahnhof", 10, 10, false)
        ));
    }

    static TimetabledOrderDTO generateTimetabledOrderDto() {
        return new TimetabledOrderDTO(2, "Rheinstetten Bahnhof", 10, 20, false);
    }
}
