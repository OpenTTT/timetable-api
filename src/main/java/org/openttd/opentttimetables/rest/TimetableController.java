package org.openttd.opentttimetables.rest;

import org.modelmapper.ModelMapper;
import org.openttd.opentttimetables.model.Destination;
import org.openttd.opentttimetables.model.Timetable;
import org.openttd.opentttimetables.model.TimetabledOrder;
import org.openttd.opentttimetables.repo.DestinationRepo;
import org.openttd.opentttimetables.repo.TimetableRepo;
import org.openttd.opentttimetables.rest.dto.MapperService;
import org.openttd.opentttimetables.rest.dto.TimetableDTO;
import org.openttd.opentttimetables.rest.dto.TimetabledOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class TimetableController {
    @Autowired
    private MapperService mapper;

    @Autowired
    private TimetableRepo timetableRepo;

    @Autowired
    private DestinationRepo destinationRepo;

    @RequestMapping(path = {"/timetable", "/timetables"})
    public List<TimetableDTO> getAllTimetables() {
        return mapper.mapAll(timetableRepo.findAll(), TimetableDTO.class);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/timetable")
    public TimetableDTO createNewTimetable(@RequestBody @Valid TimetableDTO dto) {
        Timetable timetable = mapper.map(dto, Timetable.class);
        timetable.setOrders(dto.mapOrders(mapper, destinationRepo));
        return mapper.map(timetableRepo.save(timetable), TimetableDTO.class);
    }
}
