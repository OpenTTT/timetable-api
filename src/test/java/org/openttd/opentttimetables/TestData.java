package org.openttd.opentttimetables;

import org.openttd.opentttimetables.model.Destination;
import org.openttd.opentttimetables.model.ScheduledDispatch;
import org.openttd.opentttimetables.model.Timetable;
import org.openttd.opentttimetables.model.TimetabledOrder;

import java.util.List;
import java.util.Map;

public class TestData {
    private TestData() {}

    public static final Map<String, Destination> VIRM4_MP_DESTINATIONS = Map.of(
            "Rosenheim", Destination.station("Rosenheim"),
            "Rheinstetten", Destination.station("Rheinstetten Bahnhof"),
            "Crailsheim", Destination.station("R-Crailsheim"),
            "Obernburg", Destination.station("Obernburg"),
            "Veringenstadt", Destination.station("Veringenstadt")
    );

    public static final TimetabledOrder SINGLE_ORDER = new TimetabledOrder(VIRM4_MP_DESTINATIONS.get("Rheinstetten"), 1, 9);

    public static final Timetable SIMPLE_ORDERS = new Timetable("S1", List.of(
            new TimetabledOrder(VIRM4_MP_DESTINATIONS.get("Rheinstetten"), 1, 9),
            new TimetabledOrder(VIRM4_MP_DESTINATIONS.get("Crailsheim"), 1, 9)
    ));

    public static final Timetable VIRM4_MP_ORDERS = new Timetable("RB1", List.of(
            new TimetabledOrder(VIRM4_MP_DESTINATIONS.get("Rosenheim"), 10, 17),
            new TimetabledOrder(VIRM4_MP_DESTINATIONS.get("Rheinstetten"), 3, 8),
            new TimetabledOrder(VIRM4_MP_DESTINATIONS.get("Crailsheim"), 2, 10),
            new TimetabledOrder(VIRM4_MP_DESTINATIONS.get("Obernburg"), 3, 12),
            new TimetabledOrder(VIRM4_MP_DESTINATIONS.get("Veringenstadt"), 10, 13), // Turns around
            new TimetabledOrder(VIRM4_MP_DESTINATIONS.get("Obernburg"), 5, 11),
            new TimetabledOrder(VIRM4_MP_DESTINATIONS.get("Crailsheim"), 2, 7),
            new TimetabledOrder(VIRM4_MP_DESTINATIONS.get("Rheinstetten"), 4, 16)
    ));

    public static final ScheduledDispatch SIMPLE_DISPATCH = new ScheduledDispatch(
            60,
            List.of(0, 30),
            SIMPLE_ORDERS
    );
}
