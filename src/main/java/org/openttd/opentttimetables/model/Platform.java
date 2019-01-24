package org.openttd.opentttimetables.model;

import lombok.*;

import java.util.List;

@Data
@Builder
public class Platform {
    private List<Order> connectedRoutes;
}
