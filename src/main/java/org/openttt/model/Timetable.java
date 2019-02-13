package org.openttt.model;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of this timetable, such as "RailJet 3" or "Flefingbridge Express"
     */
    private String name;

    /**
     * The getOrders the timetable encompasses
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "timetable", orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderColumn
    private List<TimetabledOrder> orders;

    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<Tag> tags;

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

    @Override
    public String toString() {
        return name;
    }
}
