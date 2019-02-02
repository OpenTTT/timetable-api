package org.openttd.opentttimetables.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimetabledOrderDTO {
    private Integer id;

    @NotEmpty
    private String destination;

    @Min(0)
    @Max(256)
    private Integer stayingTime;

    @Min(0)
    @Max(256)
    private Integer travelingTime;
    private boolean returnOrder = false;
}
