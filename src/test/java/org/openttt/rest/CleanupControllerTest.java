package org.openttt.rest;

import org.junit.After;
import org.openttt.repo.DestinationRepo;
import org.openttt.repo.ScheduledDispatchRepo;
import org.openttt.repo.TimetableRepo;
import org.openttt.repo.TimetabledOrderRepo;
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
