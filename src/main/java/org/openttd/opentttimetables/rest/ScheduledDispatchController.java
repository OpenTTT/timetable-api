package org.openttd.opentttimetables.rest;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import org.modelmapper.ModelMapper;
import org.openttd.opentttimetables.model.Schedule;
import org.openttd.opentttimetables.model.ScheduledDispatch;
import org.openttd.opentttimetables.model.ScheduledOrder;
import org.openttd.opentttimetables.repo.ScheduledDispatchRepo;
import org.openttd.opentttimetables.repo.TimetableRepo;
import org.openttd.opentttimetables.rest.dto.*;
import org.openttd.opentttimetables.scheduling.ScheduleSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = {"/scheduled-dispatches", "/scheduled-dispatch"})
public class ScheduledDispatchController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TimetableRepo timetableRepo;

    @Autowired
    private ScheduledDispatchRepo scheduledDispatchRepo;

    // TODO: check if id -> scheduled dispatch conversion can be done automatically? Spring boot surely has something here!
    @GetMapping
    public List<ScheduledDispatchDTO> getAllDispatches() {
        return Streams.stream(scheduledDispatchRepo.findAll())
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public ScheduledDispatchDTO getScheduledDispatch(@PathVariable Integer id) {
        // TODO: Defensive programming! .get() is evil!
        return toDto(scheduledDispatchRepo.findById(id).get());
    }

    @PutMapping(path = "/{id}")
    @CrossOrigin("*")
    public ScheduledDispatchDTO updateScheduledDispatch(@PathVariable Integer id, @Valid @RequestBody ScheduledDispatchDTO dto) {
        return toDto(scheduledDispatchRepo.save(fromDto(dto)));
    }

    @PostMapping
    @CrossOrigin("*")
    public ScheduledDispatchDTO createScheduledDispatch(@Valid @RequestBody ScheduledDispatchDTO dto) {
        return toDto(scheduledDispatchRepo.save(fromDto(dto)));
    }

    // TODO: Add @RequestParam here, too!
    @GetMapping(path = "/{id}/departures")
    public List<ScheduleDTO> getDeparturesForSchedule(@PathVariable Integer id) {
        ScheduledDispatch dispatch = scheduledDispatchRepo.findById(id).get();
        return generateSchedules(dispatch) // TODO hardcoded value! make query param
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // TODO: request param is not documented
    @GetMapping(path = "/{id}/departures-by-station")
    public List<SchedulesByStationDTO> getDeparturesForScheduleByStation(
            @PathVariable Integer id,
            @RequestParam(value = "numberOfDepartures", required = false, defaultValue = "5") Integer numberOfDepartures) {
        ScheduledDispatch dispatch = scheduledDispatchRepo.findById(id).get();
        List<Schedule> schedules = generateSchedules(dispatch, numberOfDepartures).collect(Collectors.toList());
        return toByStationDtos(schedules);
    }

    private Stream<Schedule> generateSchedules(ScheduledDispatch dispatch) {
        // TODO: is this method needed?
        return generateSchedules(dispatch, 5);
    }

    private Stream<Schedule> generateSchedules(ScheduledDispatch dispatch, Integer numberOfDepartures) {
        return Stream.generate(new ScheduleSupplier(dispatch))
                .limit(numberOfDepartures);

    }

    private ScheduledDispatchDTO toDto(ScheduledDispatch dispatch) {
        ScheduledDispatchDTO dto = mapper.map(dispatch, ScheduledDispatchDTO.class);
        dto.setTimetableId(dispatch.getTimetable().getId());
        dto.setTimetable(dispatch.getTimetable().getName());
        return dto;
    }

    private ScheduledDispatch fromDto(ScheduledDispatchDTO dto) {
        ScheduledDispatch scheduledDispatch = mapper.map(dto, ScheduledDispatch.class);
        // TODO: fix direct get()
        scheduledDispatch.setTimetable(timetableRepo.findById(dto.getTimetableId()).get());
        return scheduledDispatch;
    }

    // TODO: Extract dto conversion logic out of controller!
    // TODO: all methods are named toDto! Yuk!
    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private ScheduleDTO toDto(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setOrders(
                schedule.getOrders()
                        .stream()
                        .map(this::toDto)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    private ScheduledOrderDTO toDto(ScheduledOrder order) {
        ScheduledOrderDTO dto = mapper.map(order, ScheduledOrderDTO.class);
        dto.setDestination(order.getDestination().getName());
        return dto;
    }


    private List<SchedulesByStationDTO> toByStationDtos(List<Schedule> schedules) {
        List<ScheduledOrder> arbitraryOrderList = schedules.get(0).getOrders();
        List<SchedulesByStationDTO> dtos = Lists.newArrayListWithExpectedSize(arbitraryOrderList.size());
        for (int i = 0; i < arbitraryOrderList.size(); i++) {
            SchedulesByStationDTO byStationDto = new SchedulesByStationDTO();
            byStationDto.setStation(arbitraryOrderList.get(i).getDestination().getName());

            List<ScheduleDepartureDTO> departureDtos = Lists.newArrayListWithExpectedSize(schedules.size());
            for (Schedule schedule : schedules) {
                ScheduledOrder orderForThisRowAndColumn = schedule.getOrders().get(i);
                departureDtos.add(new ScheduleDepartureDTO(
                        FORMATTER.format(orderForThisRowAndColumn.getArrival()),
                        FORMATTER.format(orderForThisRowAndColumn.getDeparture())
                ));
            }

            byStationDto.setDepartures(departureDtos);
            dtos.add(byStationDto);
        }

        return dtos;
    }
}
