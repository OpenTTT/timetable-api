package org.openttt.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScheduleDTO {
    private List<ScheduledOrderDTO> orders;
}
