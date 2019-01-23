package org.openttd.opentttimetables.model;

import lombok.*;

@Data
@Builder
public class Order {
    private String name;
    private RouteNode destination;
}
