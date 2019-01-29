package org.openttd.opentttimetables;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@RestController
public class OpenTTTimetablesApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenTTTimetablesApplication.class, args);
    }
}
