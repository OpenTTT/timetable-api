package org.openttt.services;

import org.openttt.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BatchService {
    @Autowired
    private DestinationRepo destinationRepo;

    @Autowired
    private ScheduledDispatchRepo scheduledDispatchRepo;

    @Autowired
    private TagRepo tagRepo;

    @Autowired
    private TimetabledOrderRepo timetabledOrderRepo;

    @Autowired
    private TimetableRepo timetableRepo;

    @Transactional
    public void deleteAll() {
        scheduledDispatchRepo.deleteAll();
        timetableRepo.deleteAll();
        timetabledOrderRepo.deleteAll();
        tagRepo.deleteAll();
        destinationRepo.deleteAll();
    }
}
