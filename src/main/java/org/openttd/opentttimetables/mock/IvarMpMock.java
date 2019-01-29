package org.openttd.opentttimetables.mock;

import org.openttd.opentttimetables.model.Destination;
import org.openttd.opentttimetables.model.ScheduledDispatch;
import org.openttd.opentttimetables.model.TimetabledOrder;
import org.openttd.opentttimetables.model.ScheduledOrder;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class IvarMpMock {
    public static List<Destination> destinations = Arrays.asList(
            Destination.station("Rosenheim"),
            Destination.station("Rheinstetten Bahnhof"),
            Destination.station("R-Crailsheim"),
            Destination.station("Obernburg"),
            Destination.station("Veringenstadt")
    );

    public static List<TimetabledOrder> orders = Arrays.asList(
            new TimetabledOrder(destinations.get(0), 10, 17),
            new TimetabledOrder(destinations.get(1), 3, 8),
            new TimetabledOrder(destinations.get(2), 2, 10),
            new TimetabledOrder(destinations.get(3), 3, 12),
            new TimetabledOrder(destinations.get(4), 10, 13), // Turns around
            new TimetabledOrder(destinations.get(3), 5, 11),
            new TimetabledOrder(destinations.get(2), 2, 7),
            new TimetabledOrder(destinations.get(1), 4, 16)
    );

    public static ScheduledDispatch dispatch = new ScheduledDispatch(
            Arrays.asList(LocalTime.of(6, 0), LocalTime.of(6, 45)),
            90,
            orders
    );
}
