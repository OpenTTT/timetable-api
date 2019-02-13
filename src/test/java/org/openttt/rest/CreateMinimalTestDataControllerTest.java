package org.openttt.rest;

import org.junit.Before;
import org.openttt.model.*;
import org.openttt.rest.dto.ScheduledDispatchDTO;

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
    List<Tag> tags;

    @Before
    public void createMinimalTestData() {
        this.destinations = DESTINATIONS.stream()
                .map(d -> destinationRepo.save(d))
                .collect(Collectors.toList());

        this.orders = List.of(
                new TimetabledOrder(this.destinations.get(0), 1, 9),
                new TimetabledOrder(this.destinations.get(1), 1, 9)
        );

        Timetable rb1 = new Timetable("RB1", this.orders);
        Timetable rb1Reversed = new Timetable("RB1-reversed", List.of(
                new TimetabledOrder(this.destinations.get(1), 1, 9),
                new TimetabledOrder(this.destinations.get(0), 1, 9)
        ));

        this.timetables = List.of(rb1, rb1Reversed)
                .stream()
                .map(timetable -> timetableRepo.save(timetable))
                .collect(Collectors.toList());

        this.dispatches = List.of(new ScheduledDispatch(60, List.of(0, 30), this.timetables.get(0)))
                .stream()
                .map(dispatch -> scheduledDispatchRepo.save(dispatch))
                .collect(Collectors.toList());

        this.tags = List.of(new Tag(null, "S-Bahn", "#ffffff", "#ffffff"))
                .stream()
                .map(tag -> tagRepo.save(tag))
                .collect(Collectors.toList());
    }

    // helper methods
    String urlForTimetable(int timetableIndex) {
        return "/timetables/" + this.timetables.get(timetableIndex).getId() + "/";
    }

    String urlForTimetabledOrder(int timetableIndex, int orderIndex) {
        return "/timetables/" + this.timetables.get(timetableIndex).getId() + "/orders/" + this.orders.get(orderIndex).getId() + "/";
    }

    String urlForScheduledDispatch(int scheduledDispatchIndex) {
        return "/scheduled-dispatches/" + this.dispatches.get(scheduledDispatchIndex).getId();
    }

    String urlForScheduledDispatchDepartures(int scheduledDispatchIndex) {
        return "/scheduled-dispatches/" + this.dispatches.get(scheduledDispatchIndex).getId() + "/departures";
    }

    String urlForScheduledDispatchDeparturesByStation(int scheduledDispatchIndex) {
        return "/scheduled-dispatches/" + this.dispatches.get(scheduledDispatchIndex).getId() + "/departures-by-station";
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
