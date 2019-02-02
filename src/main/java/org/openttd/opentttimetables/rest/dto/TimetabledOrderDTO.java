package org.openttd.opentttimetables.rest.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class TimetabledOrderDTO {
    private Integer id;
    private String destination;

    @Min(0)
    @Max(256)
    private Integer stayingTime;

    @Min(0)
    @Max(256)
    private Integer travelingTime;
    private boolean returnOrder = false;
}
