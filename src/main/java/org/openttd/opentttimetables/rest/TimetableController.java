package org.openttd.opentttimetables.rest;

import org.modelmapper.ModelMapper;
import org.openttd.opentttimetables.model.Timetable;
import org.openttd.opentttimetables.model.TimetabledOrder;
import org.openttd.opentttimetables.repo.DestinationRepo;
import org.openttd.opentttimetables.repo.TimetableRepo;
import org.openttd.opentttimetables.rest.dto.TimetableDTO;
import org.openttd.opentttimetables.rest.dto.TimetabledOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class TimetableController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TimetableRepo timetableRepo;

    @Autowired
    private DestinationRepo destinationRepo;

    @RequestMapping(path = {"/timetable", "/timetables"})
    public Iterable<TimetableDTO> getAllTimetables() {
        return StreamSupport.stream(timetableRepo.findAll().spliterator(), true)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/timetable")
    public TimetableDTO createNewTimetable(@RequestBody @Valid TimetableDTO dto) {
        Timetable timetable = timetableRepo.save(fromDto(dto));
        return toDto(timetable);
    }

    private TimetableDTO toDto(Timetable timetable) {
        TimetableDTO dto = mapper.map(timetable, TimetableDTO.class);
        dto.setOrders(timetable.getOrders().stream()
                .map(this::toDto)
                .collect(Collectors.toList())
        );
        return dto;
    }

    private TimetabledOrderDTO toDto(TimetabledOrder order) {
        TimetabledOrderDTO dto = mapper.map(order, TimetabledOrderDTO.class);
        dto.setDestination(order.getDestination().getName());
        return dto;
    }

    private Timetable fromDto(TimetableDTO dto) {
        Timetable timetable = mapper.map(dto, Timetable.class);
        timetable.setOrders(dto
                .getOrders()
                .stream()
                .map(this::fromDto)
                .collect(Collectors.toList()));
        return timetable;
    }

    private TimetabledOrder fromDto(TimetabledOrderDTO dto) {
        TimetabledOrder order = mapper.map(dto, TimetabledOrder.class);
        order.setDestination(destinationRepo.findByName(dto.getDestination()));
        return order;
    }

}
