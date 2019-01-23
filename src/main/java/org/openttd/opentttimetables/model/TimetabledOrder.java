package org.openttd.opentttimetables.model;

import lombok.*;

@Data
@Builder
public class TimetabledOrder {
    private Order order;
    private Integer stayingTime;
    private Integer travelingTime;
}
