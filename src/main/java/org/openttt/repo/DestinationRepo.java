package org.openttt.repo;

import org.openttt.model.Destination;
import org.springframework.data.repository.CrudRepository;

public interface DestinationRepo extends CrudRepository<Destination, String> {
    Destination findByName(String name);
}
