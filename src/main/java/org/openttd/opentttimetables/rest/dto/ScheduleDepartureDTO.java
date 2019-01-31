package org.openttd.opentttimetables.rest.dto;

import java.time.LocalTime;

public class ScheduleDepartureDTO {
    private LocalTime arrival;
    private LocalTime departure;

    public ScheduleDepartureDTO(LocalTime arrival, LocalTime departure) {
        this.arrival = arrival;
        this.departure = departure;
    }

    public LocalTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalTime arrival) {
        this.arrival = arrival;
    }

    public LocalTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalTime departure) {
        this.departure = departure;
    }
}
