package org.openttd.opentttimetables.rest;

import org.junit.Before;
import org.openttd.opentttimetables.model.Destination;
import org.openttd.opentttimetables.model.ScheduledDispatch;
import org.openttd.opentttimetables.model.Timetable;
import org.openttd.opentttimetables.model.TimetabledOrder;

import java.util.List;
import java.util.stream.Collectors;

public abstract class CreateMinimalTestDataControllerTest extends CleanupControllerTest {
    private static final List<Destination> DESTINATIONS = List.of(
        Destination.station("Rosenheim"),
        Destination.station("Rheinstetten Bahnhof")
    );

    List<Destination> savedDestinations;
    List<TimetabledOrder> orders;
    List<Timetable> timetables;
    List<ScheduledDispatch> dispatches;

    @Before
    public void createMinimalTestData() {
        this.savedDestinations = DESTINATIONS.stream()
                .map(d -> destinationRepo.save(d))
                .collect(Collectors.toList());

        this.orders = List.of(
                new TimetabledOrder(this.savedDestinations.get(0), 1, 9),
                new TimetabledOrder(this.savedDestinations.get(1), 1, 9)
        );

        this.timetables = List.of(new Timetable("RB1", this.orders))
                .stream()
                .map(timetable -> timetableRepo.save(timetable))
                .collect(Collectors.toList());

        this.dispatches = List.of(new ScheduledDispatch(60, List.of(0, 30), this.timetables.get(0)))
                .stream()
                .map(dispatch -> scheduledDispatchRepo.save(dispatch))
                .collect(Collectors.toList());
    }

    // helper methods
    String urlForTimetable(int timetableIndex) {
        return "/timetable/" + this.timetables.get(timetableIndex).getId() + "/";
    }

    String urlForTimetabledOrder(int timetableIndex, int orderIndex) {
        return "/timetable/" + this.timetables.get(timetableIndex).getId() + "/order/" + this.orders.get(orderIndex).getId() + "/";
    }

    String urlForScheduledDispatch(int scheduledDispatchIndex) {
        return "/scheduled-dispatch/" + this.dispatches.get(scheduledDispatchIndex).getId();
    }
}
