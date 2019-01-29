package org.openttd.opentttimetables.model;

import lombok.*;

/**
 * The most fundamental building block of timetabling.
 *
 * True to OpenTTDs notation, it is defined as the station it is going to, and the time it takes it to get to the next
 * station.
 */
@Data
@Builder
public class TimetabledOrder {
    private Destination destination;
    private Integer stayingTime;
    private Integer travelingTime;
    private boolean finalLeg = false;

    public TimetabledOrder(Destination destination, Integer stayingTime, Integer travelingTime) {
        this.destination = destination;
        this.stayingTime = stayingTime;
        this.travelingTime = travelingTime;
    }

    public TimetabledOrder(Destination destination, Integer stayingTime, Integer travelingTime, boolean finalLeg) {
        this.destination = destination;
        this.stayingTime = stayingTime;
        this.travelingTime = travelingTime;
        this.finalLeg = finalLeg;
    }

    public Destination getDestination() {
        return destination;
    }

    public Integer getStayingTime() {
        return stayingTime;
    }

    public Integer getTravelingTime() {
        return travelingTime;
    }

    public boolean isFinalLeg() {
        return finalLeg;
    }
}
