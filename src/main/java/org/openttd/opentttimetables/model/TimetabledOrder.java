package org.openttd.opentttimetables.model;

import lombok.*;

@Data
@Builder
public class TimetabledOrder {
    private Destination destination;
    private Integer stayingTime;
    private Integer travelingTime;
}
