package org.openttd.opentttimetables.rest;

import com.google.common.collect.Lists;
import org.openttd.opentttimetables.model.ScheduledDispatch;
import org.openttd.opentttimetables.repo.DestinationRepo;
import org.openttd.opentttimetables.repo.ScheduledDispatchRepo;
import org.openttd.opentttimetables.repo.TimetableRepo;
import org.openttd.opentttimetables.rest.dto.StatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/stats")
public class StatsController {
    @Autowired
    private DestinationRepo destinationRepo;

    @Autowired
    private ScheduledDispatchRepo scheduledDispatchRepo;

    @Autowired
    private TimetableRepo timetableRepo;

    @GetMapping
    public StatsDTO getStats() {
        long totalDestinations = destinationRepo.count();
        long totalTimetables = timetableRepo.count();

        ArrayList<ScheduledDispatch> scheduledDispatches = Lists.newArrayList(scheduledDispatchRepo.findAll());
        long totalDispatches = scheduledDispatches.size();
        long totalDepartures = scheduledDispatches.stream().mapToInt(sd -> sd.getDepartures().size()).sum();

        return new StatsDTO(totalDestinations, totalDispatches, totalDepartures, totalTimetables);
    }
}
