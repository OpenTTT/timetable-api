package org.openttt.model;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Direct represenation of the "Scheduled Dispatch" dialogue in OpenTTD.
 *
 * Contains methods to generate single schedules for the dispatch based on its timetabled getOrders.
 */
@Getter
@Setter
@Entity
public class ScheduledDispatch {
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * The interval after which the dispatching continues.
     */
    private Integer intervalInMinutes;
    /**
     * The departures within the interval, as offset from the start time in minutes.
     */
    @ElementCollection
    private List<Integer> departures;
    /**
     * The getOrders this scheduled dispatch should encompass.
     */
    @ManyToOne
    private Timetable timetable;

    public ScheduledDispatch() {
        // JPA constructor
    }

    public ScheduledDispatch(Integer intervalInMinutes, List<Integer> departures, Timetable timetable) {
        Preconditions.checkArgument(intervalInMinutes > 0, "interval must be larger than zero.");
        Preconditions.checkArgument(!departures.isEmpty(), "departures may not be empty");
        Preconditions.checkArgument(departures.stream().noneMatch(i -> i >= intervalInMinutes),
                "no departure may be later than the interval");
        Preconditions.checkArgument(timetable.getOrders().size() >= 2, "order list must contain two getOrders");

        this.intervalInMinutes = intervalInMinutes;
        this.departures = departures;
        this.timetable = timetable;
    }
}
