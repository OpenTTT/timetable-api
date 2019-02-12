package org.openttd.opentttimetables.repo;

import org.openttd.opentttimetables.model.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepo extends CrudRepository<Tag, Integer> {
}
