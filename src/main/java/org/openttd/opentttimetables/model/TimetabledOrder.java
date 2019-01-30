package org.openttd.opentttimetables.model;

import javax.persistence.*;

/**
 * The most fundamental building block of scheduling.
 *
 * True to OpenTTDs notation, it is defined as the station it is going to, and the time it takes it to get to the next
 * station.
 */
@Entity
public class TimetabledOrder {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Destination destination;
    private Integer stayingTime;
    private Integer travelingTime;
    private boolean returnOrder = false;

    public TimetabledOrder() {
    }

    public TimetabledOrder(Destination destination, Integer stayingTime, Integer travelingTime) {
        this.destination = destination;
        this.stayingTime = stayingTime;
        this.travelingTime = travelingTime;
    }

    public TimetabledOrder(Destination destination, Integer stayingTime, Integer travelingTime, boolean finalLeg) {
        this.destination = destination;
        this.stayingTime = stayingTime;
        this.travelingTime = travelingTime;
        this.returnOrder = finalLeg;
    }

    public static TimetabledOrder returnOrder(Destination destination) {
        return new TimetabledOrder(destination, Integer.MAX_VALUE, Integer.MAX_VALUE, true);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void setStayingTime(Integer stayingTime) {
        this.stayingTime = stayingTime;
    }

    public void setTravelingTime(Integer travelingTime) {
        this.travelingTime = travelingTime;
    }

    public void setReturnOrder(boolean returnOrder) {
        this.returnOrder = returnOrder;
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

    public boolean isReturnOrder() {
        return returnOrder;
    }
}
