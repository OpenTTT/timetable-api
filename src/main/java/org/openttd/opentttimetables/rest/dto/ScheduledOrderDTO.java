package org.openttd.opentttimetables.rest.dto;

import java.time.LocalTime;

public class ScheduledOrderDTO {
    private String destination;
    private LocalTime arrival;
    private LocalTime departure;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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
