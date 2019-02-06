package org.openttd.opentttimetables.rest;

import org.openttd.opentttimetables.model.Timetable;
import org.openttd.opentttimetables.repo.DestinationRepo;
import org.openttd.opentttimetables.repo.TimetableRepo;
import org.openttd.opentttimetables.repo.TimetabledOrderRepo;
import org.openttd.opentttimetables.rest.dto.MapperService;
import org.openttd.opentttimetables.rest.dto.TimetableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = {"/timetable", "/timetables"})
public class TimetableController {
    @Autowired
    private MapperService mapper;

    @Autowired
    private TimetableRepo timetableRepo;

    @Autowired
    private TimetabledOrderRepo timetabledOrderRepo;

    @Autowired
    private DestinationRepo destinationRepo;

    @GetMapping
    public List<TimetableDTO> getAllTimetables() {
        return mapper.mapAll(timetableRepo.findAll(), TimetableDTO.class);
    }

    @GetMapping(path = {"/{id}"})
    public TimetableDTO getTimetable(@PathVariable("id") Integer id) {
        return mapper.map(
                timetableRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)),
                TimetableDTO.class
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimetableDTO createNewTimetable(@RequestBody @Valid TimetableDTO dto) {
        return upsertTimetable(dto);
    }

    private TimetableDTO upsertTimetable(@RequestBody @Valid TimetableDTO dto) {
        Timetable timetable = mapper.map(dto, Timetable.class);
        timetable.setOrders(dto.mapOrders(mapper, destinationRepo, timetable));
        return mapper.map(timetableRepo.save(timetable), TimetableDTO.class);
    }

    @PutMapping
    @CrossOrigin(origins = {"*"})
    public TimetableDTO updateTimetable(@RequestBody @Valid TimetableDTO dto) {
        return upsertTimetable(dto);
    }
}
