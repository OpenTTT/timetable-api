package org.openttd.opentttimetables.repo;

import org.openttd.opentttimetables.model.Timetable;
import org.springframework.data.repository.CrudRepository;

public interface TimetableRepo extends CrudRepository<Timetable, Integer> {
}
