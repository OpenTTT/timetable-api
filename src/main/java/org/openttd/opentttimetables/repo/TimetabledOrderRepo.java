package org.openttd.opentttimetables.repo;

import org.openttd.opentttimetables.model.Timetable;
import org.openttd.opentttimetables.model.TimetabledOrder;
import org.springframework.data.repository.CrudRepository;

public interface TimetabledOrderRepo extends CrudRepository<TimetabledOrder, Integer> {
    TimetabledOrder findByTimetableAndId(Timetable timetableId, Integer id);
}
