package org.openttd.opentttimetables;

import org.openttd.opentttimetables.mock.IvarMpMock;
import org.openttd.opentttimetables.model.Schedule;
import org.openttd.opentttimetables.model.ScheduledOrder;
import org.openttd.opentttimetables.timetabling.Schedules;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@RestController
public class OpenTTTimetablesApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenTTTimetablesApplication.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            List<Schedule> schedules = Schedules.nextTwentyFive(IvarMpMock.dispatch);

            for (Schedule schedule : schedules) {
                System.out.println("Departure at " + schedule.getFirstDeparture() + ":");
                printSchedule(schedule);
            }
        };
    }

    private void printSchedule(Schedule schedule) {
        String tabs = "\t\t";
        for (ScheduledOrder order : schedule.getOrders()) {
            System.out.print("\t");
            System.out.print(order.getArrival());
            System.out.print(tabs);
            System.out.print(order.getDeparture());
            System.out.print(tabs);
            System.out.print(order.getDestination());
            System.out.println();
        }

    }

}
