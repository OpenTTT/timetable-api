package org.openttd.opentttimetables.rest.dto;

import java.util.List;

public class ScheduledDispatchDTO {
    private Integer id;
    private Integer intervalInMinutes;
    private List<Integer> departures;
    private String timetable;

    public Integer getIntervalInMinutes() {
        return intervalInMinutes;
    }

    public void setIntervalInMinutes(Integer intervalInMinutes) {
        this.intervalInMinutes = intervalInMinutes;
    }

    public List<Integer> getDepartures() {
        return departures;
    }

    public Integer getId() {
        return id;
    }

    public void setDepartures(List<Integer> departures) {
        this.departures = departures;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
