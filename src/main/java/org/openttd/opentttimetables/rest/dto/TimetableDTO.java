package org.openttd.opentttimetables.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openttd.opentttimetables.model.Timetable;
import org.openttd.opentttimetables.model.TimetabledOrder;
import org.openttd.opentttimetables.repo.DestinationRepo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimetableDTO {
    private Integer id;
    @NotEmpty
    private String name;
    @Size(min = 2)
    private List<TimetabledOrderDTO> orders;

    public List<TimetabledOrder> mapOrders(MapperService mapper, DestinationRepo destinationRepo, Timetable timetable) {
        return orders.stream()
                .map(orderDto -> {
                    TimetabledOrder timetabledOrder = mapper.map(orderDto, TimetabledOrder.class);
                    timetabledOrder.setDestination(destinationRepo.findByName(orderDto.getDestination()));
                    timetabledOrder.setTimetable(timetable);
                    return timetabledOrder;
                }).collect(Collectors.toList());
    }
}
