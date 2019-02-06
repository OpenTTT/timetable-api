package org.openttd.opentttimetables.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openttd.opentttimetables.rest.dto.DestinationDTO;
import org.openttd.opentttimetables.rest.dto.TimetableDTO;
import org.openttd.opentttimetables.rest.dto.TimetabledOrderDTO;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

abstract class ControllerTest {
    DestinationDTO readDestination(ObjectMapper mapper, MvcResult result) throws java.io.IOException {
        return mapper.readValue(result.getResponse().getContentAsString(), DestinationDTO.class);
    }

    List<DestinationDTO> readListOfDestinations(ObjectMapper mapper, MvcResult result) throws java.io.IOException {
        return mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<DestinationDTO>>() {
        });
    }

    TimetableDTO readTimetable(ObjectMapper mapper, MvcResult result) throws Exception {
        return mapper.readValue(result.getResponse().getContentAsString(), TimetableDTO.class);
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
