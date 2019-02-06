package org.openttd.opentttimetables;

import org.openttd.opentttimetables.model.Destination;
import org.openttd.opentttimetables.model.ScheduledDispatch;
import org.openttd.opentttimetables.model.Timetable;
import org.openttd.opentttimetables.model.TimetabledOrder;
import org.openttd.opentttimetables.repo.DestinationRepo;
import org.openttd.opentttimetables.repo.ScheduledDispatchRepo;
import org.openttd.opentttimetables.repo.TimetableRepo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile({"dev"})
public class CreateFakeDataBean implements InitializingBean {
    private static final List<Destination> DESTINATIONS = List.of(
        Destination.station("Rosenheim"),
        Destination.station("Rheinstetten Bahnhof"),
        Destination.station("R-Crailsheim"),
        Destination.station("Obernburg"),
        Destination.station("Veringenstadt")
    );

    private static final List<Timetable> TIMETABLES = List.of(
            new Timetable("RB 1", List.of(
                    new TimetabledOrder(DESTINATIONS.get(0), 10, 17),
                    new TimetabledOrder(DESTINATIONS.get(1), 3, 8),
                    new TimetabledOrder(DESTINATIONS.get(2), 2, 10),
                    new TimetabledOrder(DESTINATIONS.get(3), 3, 12),
                    new TimetabledOrder(DESTINATIONS.get(4), 10, 13), // Turns around
                    new TimetabledOrder(DESTINATIONS.get(3), 5, 11),
                    new TimetabledOrder(DESTINATIONS.get(2), 2, 7),
                    new TimetabledOrder(DESTINATIONS.get(1), 4, 16)
            ))
    );

    private static final ScheduledDispatch VIRM4_DISPATCH = new ScheduledDispatch(
            90,
            List.of(0, 45),
            TIMETABLES.get(0)
    );

    @Autowired
    private DestinationRepo destinationRepo;

    @Autowired
    private TimetableRepo timetableRepo;

    @Autowired
    private ScheduledDispatchRepo dispatchRepo;

    @Override
    public void afterPropertiesSet() throws Exception {
        for (Destination destination : DESTINATIONS) {
            destinationRepo.save(destination);
        }

        for (Timetable timetable : TIMETABLES) {
            timetableRepo.save(timetable);
        }

        dispatchRepo.save(VIRM4_DISPATCH);
    }
}
