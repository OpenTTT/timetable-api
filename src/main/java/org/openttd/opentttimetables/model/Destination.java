package org.openttd.opentttimetables.model;

import lombok.*;

@Data
@Builder
public class Destination {
    private String name;
    private RouteNode destination;
}
