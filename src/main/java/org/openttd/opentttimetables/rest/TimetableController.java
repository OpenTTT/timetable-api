package org.openttd.opentttimetables.rest;

import org.openttd.opentttimetables.model.Timetable;
import org.openttd.opentttimetables.repo.DestinationRepo;
import org.openttd.opentttimetables.repo.TimetableRepo;
import org.openttd.opentttimetables.rest.dto.MapperService;
import org.openttd.opentttimetables.rest.dto.TimetableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

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

    @RequestMapping(path = {"/timetable/{id}"})
    public TimetableDTO getTimetable(@PathVariable("id") Integer id) {
        return mapper.map(
                timetableRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                TimetableDTO.class
        );
    }
    @RequestMapping(method = RequestMethod.POST, path = "/timetable")
    public TimetableDTO createNewTimetable(@RequestBody @Valid TimetableDTO dto) {
        Timetable timetable = mapper.map(dto, Timetable.class);
        timetable.setOrders(dto.mapOrders(mapper, destinationRepo));
        return mapper.map(timetableRepo.save(timetable), TimetableDTO.class);
    }
}
