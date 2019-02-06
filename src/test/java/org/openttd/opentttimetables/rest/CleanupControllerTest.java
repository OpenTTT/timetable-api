package org.openttd.opentttimetables.rest;

import org.junit.After;
import org.openttd.opentttimetables.repo.DestinationRepo;
import org.openttd.opentttimetables.repo.ScheduledDispatchRepo;
import org.openttd.opentttimetables.repo.TimetableRepo;
import org.openttd.opentttimetables.repo.TimetabledOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CleanupControllerTest extends ControllerTest {
    @Autowired
    ScheduledDispatchRepo scheduledDispatchRepo;

    @Autowired
    TimetableRepo timetableRepo;

    @Autowired
    TimetabledOrderRepo timetabledOrderRepo;

    @Autowired
    DestinationRepo destinationRepo;

    @After
    public void cleanUp() {
        scheduledDispatchRepo.deleteAll();
        timetableRepo.deleteAll();
        timetabledOrderRepo.deleteAll();
        destinationRepo.deleteAll();
    }
}
