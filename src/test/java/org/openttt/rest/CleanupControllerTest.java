package org.openttt.rest;

import org.junit.After;
import org.openttt.repo.*;
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

    @Autowired
    TagRepo tagRepo;

    @After
    public void cleanUp() {
        scheduledDispatchRepo.deleteAll();
        timetableRepo.deleteAll();
        timetabledOrderRepo.deleteAll();
        destinationRepo.deleteAll();
        tagRepo.deleteAll();
    }
}
