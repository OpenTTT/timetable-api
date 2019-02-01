package org.openttd.opentttimetables.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimetabledOrderDTO {
    private Integer id;
    private String destination;
    private Integer stayingTime;
    private Integer travelingTime;
    private boolean returnOrder = false;
}
