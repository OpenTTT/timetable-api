package org.openttd.opentttimetables.rest.dto;

public class TimetabledOrderDTO {
    private String destination;
    private Integer stayingTime;
    private Integer travelingTime;
    private boolean returnOrder = false;

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public Integer getStayingTime() {
        return stayingTime;
    }

    public void setStayingTime(Integer stayingTime) {
        this.stayingTime = stayingTime;
    }

    public Integer getTravelingTime() {
        return travelingTime;
    }

    public void setTravelingTime(Integer travelingTime) {
        this.travelingTime = travelingTime;
    }

    public boolean isReturnOrder() {
        return returnOrder;
    }

    public void setReturnOrder(boolean returnOrder) {
        this.returnOrder = returnOrder;
    }
}
