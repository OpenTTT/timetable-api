package org.openttd.opentttimetables.mock;

import org.openttd.opentttimetables.model.*;

import java.time.LocalTime;
import java.util.Arrays;

public class ViennaMockTimetable {

    private static Destination VIENNA_BREITENSEE = Destination.builder()
        .name("Wien Breitensee")
        .nodeType(Destination.NodeType.STATION)
        .build();
    private static Destination VIENNA_OTTAKRING = Destination.builder()
            .name("Wien Ottakring")
            .nodeType(Destination.NodeType.STATION)
            .build();

    public static ScheduledDispatch VIENNA_DISPATCH = ScheduledDispatch.builder()
            .interval(60)
            .startTime(LocalTime.of(0,0))
            .orders(Arrays.asList(
                    TimetabledOrder.builder()
                            .stayingTime(1)
                            .travelingTime(5)
                            .order(Order.builder().destination(VIENNA_BREITENSEE).build())
                            .build(),
                    TimetabledOrder.builder()
                            .stayingTime(1)
                            .travelingTime(7)
                            .order(Order.builder().destination(VIENNA_OTTAKRING).build())
                            .build()
            ))
            .build();
}
