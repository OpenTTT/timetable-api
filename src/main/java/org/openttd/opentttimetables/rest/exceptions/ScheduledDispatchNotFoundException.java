package org.openttd.opentttimetables.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ScheduledDispatchNotFoundException extends ResponseStatusException {
    public ScheduledDispatchNotFoundException() {
        super(HttpStatus.NOT_FOUND, "A scheduled dispatch with this ID could not be found");
    }
}
