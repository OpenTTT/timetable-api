package org.openttt.rest.dto;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openttt.model.Schedule;
import org.openttt.model.ScheduledOrder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SchedulesByStationDTO {
    private String station;
    private List<ScheduleDepartureDTO> departures;


    public static List<SchedulesByStationDTO> fromSchedules(List<Schedule> schedules) {
        List<ScheduledOrder> arbitraryOrderList = schedules.get(0).getOrders();
        List<SchedulesByStationDTO> resultRows = Lists.newArrayListWithExpectedSize(arbitraryOrderList.size());
        for (int i = 0; i < arbitraryOrderList.size(); i++) {
            SchedulesByStationDTO row = new SchedulesByStationDTO();
            row.setStation(arbitraryOrderList.get(i).getDestination().getName());

            List<ScheduleDepartureDTO> resultColumns = Lists.newArrayListWithExpectedSize(schedules.size());
            for (Schedule schedule : schedules) {
                ScheduledOrder orderForThisRowAndColumn = schedule.getOrders().get(i);
                resultColumns.add(new ScheduleDepartureDTO(
                        ScheduledOrderDTO.TIME_FORMAT.format(orderForThisRowAndColumn.getArrival()),
                        ScheduledOrderDTO.TIME_FORMAT.format(orderForThisRowAndColumn.getDeparture())
                ));
            }

            row.setDepartures(resultColumns);
            resultRows.add(row);
        }

        return resultRows;
    }
}
