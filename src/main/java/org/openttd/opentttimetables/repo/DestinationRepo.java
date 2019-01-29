package org.openttd.opentttimetables.repo;

import org.openttd.opentttimetables.model.Destination;
import org.springframework.data.repository.CrudRepository;

public interface DestinationRepo extends CrudRepository<Destination, String> {
}
