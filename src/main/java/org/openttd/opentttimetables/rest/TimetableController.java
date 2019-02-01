package org.openttd.opentttimetables.rest;

import org.openttd.opentttimetables.model.Timetable;
import org.openttd.opentttimetables.model.TimetabledOrder;
import org.openttd.opentttimetables.repo.DestinationRepo;
import org.openttd.opentttimetables.repo.TimetableRepo;
import org.openttd.opentttimetables.repo.TimetabledOrderRepo;
import org.openttd.opentttimetables.rest.dto.MapperService;
import org.openttd.opentttimetables.rest.dto.TimetableDTO;
import org.openttd.opentttimetables.rest.dto.TimetabledOrderDTO;
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
    private TimetabledOrderRepo timetabledOrderRepo;

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

    @RequestMapping(method = RequestMethod.PUT, path = "/timetable/{timetableId}/order/{orderId}")
    public TimetabledOrderDTO updateTimetableOrder(
            @PathVariable Integer timetableId,
            @PathVariable Integer orderId,
            @Valid @RequestBody TimetabledOrderDTO dto) {
        Timetable timetable = timetableRepo.findById(timetableId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        TimetabledOrder order = timetabledOrderRepo.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!order.getTimetable().equals(timetable)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TimetabledOrder " + orderId + " does not belong to timetable " + timetableId);
        }
        if (!order.getDestination().getName().equals(dto.getDestination())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination for an existing TimetabledOrder cannot be changed");
        }

        order.setStayingTime(dto.getStayingTime());
        order.setTravelingTime(dto.getTravelingTime());

        return mapper.map(timetabledOrderRepo.save(order), TimetabledOrderDTO.class);
    }
}
