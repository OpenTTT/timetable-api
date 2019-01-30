package org.openttd.opentttimetables.repo;

import org.openttd.opentttimetables.model.ScheduledDispatch;
import org.springframework.data.repository.CrudRepository;

public interface ScheduledDispatchRepo extends CrudRepository<ScheduledDispatch, Integer> {
}
