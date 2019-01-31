package org.openttd.opentttimetables.rest.dto;

import java.util.List;

public class SchedulesByStationDTO {
    private String station;
    private List<ScheduleDepartureDTO> departures;

    public SchedulesByStationDTO() {
    }

    public SchedulesByStationDTO(String station, List<ScheduleDepartureDTO> departures) {
        this.station = station;
        this.departures = departures;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public List<ScheduleDepartureDTO> getDepartures() {
        return departures;
    }

    public void setDepartures(List<ScheduleDepartureDTO> departures) {
        this.departures = departures;
    }
}
