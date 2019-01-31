package org.openttd.opentttimetables.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TimetableDTO {
    private String name;
    private List<TimetabledOrderDTO> orders;
}
