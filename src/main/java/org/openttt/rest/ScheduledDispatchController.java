package org.openttt.rest;

import org.openttt.model.Schedule;
import org.openttt.model.ScheduledDispatch;
import org.openttt.repo.ScheduledDispatchRepo;
import org.openttt.repo.TimetableRepo;
import org.openttt.rest.dto.MapperService;
import org.openttt.rest.dto.ScheduleDTO;
import org.openttt.rest.dto.ScheduledDispatchDTO;
import org.openttt.rest.dto.SchedulesByStationDTO;
import org.openttt.rest.exceptions.ScheduledDispatchNotFoundException;
import org.openttt.scheduling.ScheduleSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/scheduled-dispatches")
@CrossOrigin("*")
public class ScheduledDispatchController {
    @Autowired
    private MapperService mapper;

    @Autowired
    private TimetableRepo timetableRepo;

    @Autowired
    private ScheduledDispatchRepo scheduledDispatchRepo;

    @GetMapping
    public List<ScheduledDispatchDTO> getAllDispatches() {
        return mapper.mapAll(scheduledDispatchRepo.findAll(), ScheduledDispatchDTO.class);
    }

    @GetMapping(path = "/{id}")
    public ScheduledDispatchDTO getScheduledDispatch(@PathVariable Integer id) {
        ScheduledDispatch dispatch = scheduledDispatchRepo.findById(id)
                .orElseThrow(ScheduledDispatchNotFoundException::new);
        return mapper.map(dispatch, ScheduledDispatchDTO.class);
    }

    @PutMapping(path = "/{id}")
    public ScheduledDispatchDTO updateScheduledDispatch(@PathVariable Integer id, @Valid @RequestBody ScheduledDispatchDTO dto) {
        if (!scheduledDispatchRepo.existsById(id)) {
            throw new ScheduledDispatchNotFoundException();
        }

        return saveDto(dto);
    }

    @PostMapping
    public ScheduledDispatchDTO createScheduledDispatch(@Valid @RequestBody ScheduledDispatchDTO dto) {
        return saveDto(dto);
    }

    @GetMapping(path = "/{id}/departures")
    public List<ScheduleDTO> getDeparturesForSchedule(
            @PathVariable Integer id,
            @RequestParam(value = "numberOfDepartures", required = false, defaultValue = "5") Integer numberOfDepartures,
            @RequestParam(value = "withReturnOrder", required = false, defaultValue = "false") Boolean withReturnOrder
    ) {
        ScheduledDispatch dispatch = scheduledDispatchRepo.findById(id)
                .orElseThrow(ScheduledDispatchNotFoundException::new);
        return mapper.mapAll(generateSchedules(dispatch, numberOfDepartures, withReturnOrder), ScheduleDTO.class);
    }

    @GetMapping(path = "/{id}/departures-by-station")
    public List<SchedulesByStationDTO> getDeparturesForScheduleByStation(
            @PathVariable Integer id,
            @RequestParam(value = "numberOfDepartures", required = false, defaultValue = "5") Integer numberOfDepartures,
            @RequestParam(value = "withReturnOrder", required = false, defaultValue = "false") Boolean withReturnOrder
    ) {
        ScheduledDispatch dispatch = scheduledDispatchRepo.findById(id)
                .orElseThrow(ScheduledDispatchNotFoundException::new);

        // This one is a special case, the transferal mapping logic is a bit too complex for ModelMapper
        List<Schedule> schedules = generateSchedules(dispatch, numberOfDepartures, withReturnOrder).collect(Collectors.toList());
        return SchedulesByStationDTO.fromSchedules(schedules);
    }

    private ScheduledDispatchDTO saveDto(ScheduledDispatchDTO dto) {
        ScheduledDispatch dispatch = mapper.map(dto, ScheduledDispatch.class);
        dispatch.setTimetable(timetableRepo.findById(dto.getTimetableId()).orElseThrow());
        ScheduledDispatch savedScheduledDispatch = scheduledDispatchRepo.save(dispatch);
        return mapper.map(savedScheduledDispatch, ScheduledDispatchDTO.class);
    }

    private Stream<Schedule> generateSchedules(ScheduledDispatch dispatch, Integer numberOfDepartures, Boolean withReturnOrder) {
        return Stream.generate(new ScheduleSupplier(dispatch, withReturnOrder))
                .limit(numberOfDepartures);

    }
}
