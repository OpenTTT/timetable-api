package org.openttt.rest;

import org.junit.Test;
import org.openttt.rest.dto.TagDTO;
import org.openttt.rest.dto.TimetableDTO;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TagsTest extends CreateMinimalTestDataControllerTest {
    @Test
    public void testGETAllTagsSucceeds() throws Exception {
        List<TagDTO> tags = readTags(mvc.perform(get("/tags")).andReturn());
        assertThat(tags).hasSameSizeAs(this.tags);
    }

    @Test
    public void testPOSTTagSucceeds() throws Exception {
        mvc.perform(post("/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(generateTagDto())))
                .andExpect(status().isOk());

        List<TagDTO> tags = readTags(mvc.perform(get("/tags")).andReturn());
        assertThat(tags).hasSize(this.tags.size() + 1);
    }

    // A test-usage oopsie :)
    @Test
    public void testThatTagsCanBeAppliedToMoreThanOneTimetable() throws Exception {
        TimetableDTO timetableOne = readTimetable(mvc.perform(get(urlForTimetable(0))).andReturn());
        timetableOne.setTags(Set.of(dtoMapper.map(tags.get(0), TagDTO.class)));

        // Saves successfully?
        mvc.perform(put(urlForTimetable(0))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(timetableOne)))
                .andExpect(status().isOk());

        // Response now contains tag?
        TimetableDTO timetableOneWithTag = readTimetable(mvc.perform(get(urlForTimetable(0))).andReturn());
        assertThat(timetableOneWithTag.getTags()).hasSize(1);

        // Setup for second timetable
        TimetableDTO timetableTwo = readTimetable(mvc.perform(get(urlForTimetable(1))).andReturn());
        timetableTwo.setTags(Set.of(dtoMapper.map(tags.get(0), TagDTO.class)));

        // Another one saves successfully?
        mvc.perform(put(urlForTimetable(1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(timetableTwo)))
                .andExpect(status().isOk());

        // Response now contains tag?
        TimetableDTO timetableTwoWithTag = readTimetable(mvc.perform(get(urlForTimetable(1))).andReturn());
        assertThat(timetableTwoWithTag.getTags()).hasSize(1);
    }
}
