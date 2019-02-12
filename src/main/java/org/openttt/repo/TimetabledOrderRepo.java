package org.openttt.repo;

import org.openttt.model.Timetable;
import org.openttt.model.TimetabledOrder;
import org.springframework.data.repository.CrudRepository;

public interface TimetabledOrderRepo extends CrudRepository<TimetabledOrder, Integer> {
    TimetabledOrder findByTimetableAndId(Timetable timetableId, Integer id);
}
