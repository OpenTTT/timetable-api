package org.openttt.rest;

import com.google.common.collect.Lists;
import org.openttt.model.ScheduledDispatch;
import org.openttt.repo.DestinationRepo;
import org.openttt.repo.ScheduledDispatchRepo;
import org.openttt.repo.TimetableRepo;
import org.openttt.rest.dto.StatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/stats")
@CrossOrigin("*")
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
