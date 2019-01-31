package org.openttd.opentttimetables.rest.dto;

import lombok.Getter;
import lombok.Setter;
import org.openttd.opentttimetables.model.TimetabledOrder;
import org.openttd.opentttimetables.repo.DestinationRepo;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TimetableDTO {
    private Integer id;
    private String name;
    private List<TimetabledOrderDTO> orders;

    public List<TimetabledOrder> mapOrders(MapperService mapper, DestinationRepo destinationRepo) {
        return orders.stream()
                .map(orderDto -> {
                    TimetabledOrder timetabledOrder = mapper.map(orderDto, TimetabledOrder.class);
                    timetabledOrder.setDestination(destinationRepo.findByName(orderDto.getDestination()));
                    return timetabledOrder;
                }).collect(Collectors.toList());
    }
}
