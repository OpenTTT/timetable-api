package org.openttd.opentttimetables.model;

import com.google.common.base.Preconditions;

import java.util.List;

public class Timetable {
    /**
     * The name of this timetable, such as "RailJet 3" or "Flefingbridge Express"
     */
    private String name;
    /**
     * The orders the timetable encompasses
     */
    private List<TimetabledOrder> orders;

    public Timetable(String name, List<TimetabledOrder> orders) {
        Preconditions.checkArgument(orders.size() >= 2, "Timetable must contain at least two orders");
        this.name = name;
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public List<TimetabledOrder> getOrders() {
        return orders;
    }
}
