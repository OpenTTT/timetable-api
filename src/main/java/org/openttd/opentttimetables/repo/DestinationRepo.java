package org.openttd.opentttimetables.repo;

import org.openttd.opentttimetables.model.Destination;
import org.openttd.opentttimetables.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface DestinationRepo extends CrudRepository<Destination, String> {
}
