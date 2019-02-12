package org.openttt.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * The most fundamental building block of scheduling.
 *
 * True to OpenTTDs notation, it is defined as the station it is going to, and the time it takes it to get to the next
 * station.
 */
@Getter
@Setter
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
    
    @ManyToOne(optional = false)
    private Timetable timetable;

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

}
