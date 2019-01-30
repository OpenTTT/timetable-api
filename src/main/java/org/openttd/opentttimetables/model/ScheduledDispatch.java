package org.openttd.opentttimetables.model;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.util.List;

/**
 * Direct represenation of the "Scheduled Dispatch" dialogue in OpenTTD.
 *
 * Contains methods to generate single schedules for the dispatch based on its timetabled orders.
 */
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
     * The orders this scheduled dispatch should encompass.
     */
    @OneToOne(optional = false)
    private Timetable timetable;

    public ScheduledDispatch() {
        // JPA constructor
    }

    public ScheduledDispatch(Integer intervalInMinutes, List<Integer> departures, Timetable timetable) {
        Preconditions.checkArgument(intervalInMinutes > 0, "interval must be larger than zero.");
        Preconditions.checkArgument(departures.size() > 0, "departures may not be empty");
        Preconditions.checkArgument(departures.stream().noneMatch(i -> i >= intervalInMinutes),
                "no departure may be later than the interval");
        Preconditions.checkArgument(timetable.getOrders().size() >= 2, "order list must contain two orders");

        this.intervalInMinutes = intervalInMinutes;
        this.departures = departures;
        this.timetable = timetable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public List<TimetabledOrder> getOrders() {
        return timetable.getOrders();
    }

    public List<Integer> getDepartures() {
        return departures;
    }

    public Integer getIntervalInMinutes() {
        return intervalInMinutes;
    }

    public void setIntervalInMinutes(Integer intervalInMinutes) {
        this.intervalInMinutes = intervalInMinutes;
    }

    public void setDepartures(List<Integer> departures) {
        this.departures = departures;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }
}
