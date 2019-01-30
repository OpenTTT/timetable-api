package org.openttd.opentttimetables.rest.dto;

import java.util.List;

public class ScheduleDTO {

    private List<ScheduledOrderDTO> orders;

    public List<ScheduledOrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<ScheduledOrderDTO> orders) {
        this.orders = orders;
    }
}
