package org.openttd.opentttimetables.rest;

import org.openttd.opentttimetables.model.Destination;
import org.openttd.opentttimetables.model.Order;
import org.openttd.opentttimetables.repo.DestinationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/destination/", "/destinations/"})
public class DestinationController {
    @Autowired private DestinationRepo repo;

    @RequestMapping("/")
    public Iterable<Destination> getDestinations() {
        return repo.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/")
    public Destination createDestination(@Valid @RequestBody Destination destination) {
        return repo.save(destination);
    }
}
