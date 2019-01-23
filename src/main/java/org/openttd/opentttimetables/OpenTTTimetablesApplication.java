package org.openttd.opentttimetables;

import org.openttd.opentttimetables.mock.ViennaMockTimetable;
import org.openttd.opentttimetables.timetabling.SingleDispatchTimetable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@RestController
public class OpenTTTimetablesApplication {
    @RequestMapping("/")
    String home() {
        return "<pre>" + new SingleDispatchTimetable(LocalTime.NOON, ViennaMockTimetable.VIENNA_DISPATCH).nextTimetableAsText();
    }

    public static void main(String[] args) {
        SpringApplication.run(OpenTTTimetablesApplication.class, args);
    }

}
