package org.openttd.opentttimetables.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents something that a route can go through, like stations or waypoints
 */
@Data
@Builder
public class Destination {
    public enum NodeType {
        STATION,
        WAYPOINT,
        DEPOT
    }

    private String name;
    private List<Platform> platforms = new ArrayList<>();
    private NodeType nodeType;
}
