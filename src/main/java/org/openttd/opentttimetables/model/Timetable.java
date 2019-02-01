package org.openttd.opentttimetables.model;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.util.List;

@Entity
public class Timetable {
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * The name of this timetable, such as "RailJet 3" or "Flefingbridge Express"
     */
    private String name;

    /**
     * The getOrders the timetable encompasses
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "timetable")
    private List<TimetabledOrder> orders;

    public Timetable() {
    }

    public Timetable(String name, List<TimetabledOrder> orders) {
        Preconditions.checkArgument(orders.size() >= 2, "Timetable must contain at least two getOrders");
        this.name = name;
        this.orders = orders;

        for (TimetabledOrder order : orders) {
            order.setTimetable(this);
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<TimetabledOrder> getOrders() {
        return orders;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrders(List<TimetabledOrder> orders) {
        this.orders = orders;
    }
}
