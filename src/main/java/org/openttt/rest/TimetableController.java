package org.openttt.rest;

import org.openttt.model.Timetable;
import org.openttt.repo.DestinationRepo;
import org.openttt.repo.TagRepo;
import org.openttt.repo.TimetableRepo;
import org.openttt.repo.TimetabledOrderRepo;
import org.openttt.rest.dto.MapperService;
import org.openttt.rest.dto.TimetableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/timetables")
@CrossOrigin("*")
public class TimetableController {
    @Autowired
    private MapperService mapper;

    @Autowired
    private TimetableRepo timetableRepo;

    @Autowired
    private TimetabledOrderRepo timetabledOrderRepo;

    @Autowired
    private DestinationRepo destinationRepo;

    @Autowired
    private TagRepo tagRepo;

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

    @PutMapping("/{id}")
    public TimetableDTO updateTimetable(@PathVariable Integer id, @RequestBody @Valid TimetableDTO dto) {
        if (!timetableRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A timetable with that ID does not exist");
        }

        return upsertTimetable(dto);
    }

    private TimetableDTO upsertTimetable(@RequestBody @Valid TimetableDTO dto) {
        Timetable timetable = mapper.map(dto, Timetable.class);
        timetable.setOrders(dto.mapOrders(mapper, destinationRepo, timetable));
        timetable.setTags(dto.mapTags(tagRepo));
        return mapper.map(timetableRepo.save(timetable), TimetableDTO.class);
    }
}
