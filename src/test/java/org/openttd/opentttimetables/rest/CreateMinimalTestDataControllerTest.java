package org.openttd.opentttimetables.rest;

import org.junit.Before;
import org.openttd.opentttimetables.model.Destination;
import org.openttd.opentttimetables.model.ScheduledDispatch;
import org.openttd.opentttimetables.model.Timetable;
import org.openttd.opentttimetables.model.TimetabledOrder;
import org.openttd.opentttimetables.rest.dto.ScheduledDispatchDTO;

import java.util.List;
import java.util.stream.Collectors;

public abstract class CreateMinimalTestDataControllerTest extends CleanupControllerTest {
    private static final List<Destination> DESTINATIONS = List.of(
        Destination.station("Rosenheim"),
        Destination.station("Rheinstetten Bahnhof")
    );

    List<Destination> destinations;
    List<TimetabledOrder> orders;
    List<Timetable> timetables;
    List<ScheduledDispatch> dispatches;

    @Before
    public void createMinimalTestData() {
        this.destinations = DESTINATIONS.stream()
                .map(d -> destinationRepo.save(d))
                .collect(Collectors.toList());

        this.orders = List.of(
                new TimetabledOrder(this.destinations.get(0), 1, 9),
                new TimetabledOrder(this.destinations.get(1), 1, 9)
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

    String urlForScheduledDispatchDepartures(int scheduledDispatchIndex) {
        return "/scheduled-dispatch/" + this.dispatches.get(scheduledDispatchIndex).getId() + "/departures";
    }

    String urlForScheduledDispatchDeparturesByStation(int scheduledDispatchIndex) {
        return "/scheduled-dispatch/" + this.dispatches.get(scheduledDispatchIndex).getId() + "/departures-by-station";
    }

    protected ScheduledDispatchDTO generateScheduledDispatchDto() {
        ScheduledDispatchDTO dto = new ScheduledDispatchDTO();
        dto.setId(dispatches.get(0).getId());
        dto.setTimetableId(timetables.get(0).getId());
        dto.setIntervalInMinutes(90);
        dto.setDepartures(List.of(15, 45));
        return dto;
    }
}
