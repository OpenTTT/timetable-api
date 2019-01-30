package org.openttd.opentttimetables.rest.dto;

import java.util.List;

public class TimetableDTO {
    private String name;
    private List<TimetabledOrderDTO> orders;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TimetabledOrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<TimetabledOrderDTO> orders) {
        this.orders = orders;
    }
}
