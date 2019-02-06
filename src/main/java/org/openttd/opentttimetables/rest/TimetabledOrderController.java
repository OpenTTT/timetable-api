package org.openttd.opentttimetables.rest;

import org.modelmapper.ModelMapper;
import org.openttd.opentttimetables.model.Timetable;
import org.openttd.opentttimetables.model.TimetabledOrder;
import org.openttd.opentttimetables.repo.TimetableRepo;
import org.openttd.opentttimetables.repo.TimetabledOrderRepo;
import org.openttd.opentttimetables.rest.dto.TimetabledOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class TimetabledOrderController {
    @Autowired
    private TimetableRepo timetableRepo;
    @Autowired
    private TimetabledOrderRepo timetabledOrderRepo;
    @Autowired
    private ModelMapper mapper;

    @PutMapping(path = "/timetable/{timetableId}/order/{orderId}")
    @CrossOrigin
    public TimetabledOrderDTO updateTimetableOrder(
            @PathVariable Integer timetableId,
            @PathVariable Integer orderId,
            @Valid @RequestBody TimetabledOrderDTO dto) {
        Timetable timetable = timetableRepo.findById(timetableId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Could not find timetable with ID " + timetableId));
        TimetabledOrder order = timetabledOrderRepo.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Could not find timetabled order with ID " + orderId));

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
