package org.openttt.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class DestinationDTO {
    @NotEmpty
    @Size(min = 1, max = 250)
    private String name;

    @NotEmpty
    @Pattern(regexp = "STATION|WAYPOINT|DEPOT")
    private String destinationType;
}
