package org.openttd.opentttimetables.rest.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class DestinationDTO {
    @NotEmpty
    @Size(min = 1, max = 250)
    private String name;

    @NotEmpty
    @Pattern(regexp = "station|waypoint|depot", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String destinationType;
}
