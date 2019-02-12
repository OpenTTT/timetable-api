package org.openttt.repo;

import org.openttt.model.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepo extends CrudRepository<Tag, Integer> {
}
