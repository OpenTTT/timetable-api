package org.openttd.opentttimetables.rest;

import org.openttd.opentttimetables.model.Destination;
import org.openttd.opentttimetables.repo.DestinationRepo;
import org.openttd.opentttimetables.rest.dto.DestinationDTO;
import org.openttd.opentttimetables.rest.dto.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/destinations")
@CrossOrigin("*")
public class DestinationController {
    @Autowired
    private MapperService mapper;

    @Autowired
    private DestinationRepo repo;

    @GetMapping
    public Iterable<DestinationDTO> getDestinations() {
        return mapper.mapAll(repo.findAll(), DestinationDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Destination createDestination(@Valid @RequestBody DestinationDTO destination) {
        return repo.save(mapper.map(destination, Destination.class));
    }
}
