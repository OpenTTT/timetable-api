package org.openttd.opentttimetables.mock;

import org.openttd.opentttimetables.model.*;

import java.time.LocalTime;
import java.util.Arrays;

public class ViennaMockTimetable {

    private static RouteNode VIENNA_BREITENSEE = RouteNode.builder()
        .name("Wien Breitensee")
        .nodeType(RouteNode.NodeType.STATION)
        .build();
    private static RouteNode VIENNA_OTTAKRING = RouteNode.builder()
            .name("Wien Ottakring")
            .nodeType(RouteNode.NodeType.STATION)
            .build();

    public static ScheduledDispatch VIENNA_DISPATCH = ScheduledDispatch.builder()
            .interval(60)
            .startTime(LocalTime.of(0,0))
            .orders(Arrays.asList(
                    TimetabledOrder.builder()
                            .stayingTime(1)
                            .travelingTime(5)
                            .order(Destination.builder().destination(VIENNA_BREITENSEE).build())
                            .build(),
                    TimetabledOrder.builder()
                            .stayingTime(1)
                            .travelingTime(7)
                            .order(Destination.builder().destination(VIENNA_OTTAKRING).build())
                            .build()
            ))
            .build();
}
