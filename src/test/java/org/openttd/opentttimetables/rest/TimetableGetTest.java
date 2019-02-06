package org.openttd.opentttimetables.rest;

import org.junit.Test;
import org.openttd.opentttimetables.rest.dto.TimetableDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class TimetableGetTest extends CreateMinimalTestDataControllerTest {
    @Test
    public void testGETOfAllTimetablesSucceeds() throws Exception {
        List<TimetableDTO> dtos = readListOfTimetables(mvc.perform(get("/timetables")).andReturn());
        assertThat(dtos).hasSize(1);
    }

    @Test
    public void testGETOfSpecificTimetableSucceeds() throws Exception {
        TimetableDTO dto = readTimetable(mvc.perform(get(urlForTimetable(0))).andReturn());
        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo(timetables.get(0).getName());
    }
}
