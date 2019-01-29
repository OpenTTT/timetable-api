package org.openttd.opentttimetables.rest;

import org.openttd.opentttimetables.model.Destination;
import org.openttd.opentttimetables.repo.DestinationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
