package org.openttd.opentttimetables.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


/**
 * Represents something that a route can go through, like stations or way points.
 */
@Getter
@Setter
@Builder
@Entity
public class Destination {
    public enum DestinationType {
        STATION,
        WAYPOINT,
        DEPOT
    }

    public Destination() {

    }

    public Destination(String name, DestinationType destinationType) {
        this.name = name;
        this.destinationType = destinationType;
    }

    @Id
    private String name;

    @NotNull
    private Destination.DestinationType destinationType;

    public static Destination station(String name) {
        return new Destination(name, DestinationType.STATION);
    }

    @Override
    public String toString() {
        return name;
    }
}
