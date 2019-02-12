package org.openttt.repo;

import org.openttt.model.Timetable;
import org.springframework.data.repository.CrudRepository;

public interface TimetableRepo extends CrudRepository<Timetable, Integer> {
    Timetable findByName(String timetable);
}
