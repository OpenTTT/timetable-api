<p align="center">
    <img src="https://raw.githubusercontent.com/opentttimetables/opentttimetables/master/media/openTTT.png" alt="OpenTTT logo">
    <h1>Timetabling API</h1>
</p>

[![Build Status](https://travis-ci.org/OpenTTT/timetable-api.svg?branch=master)](https://travis-ci.org/OpenTTT/timetable-api)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=org.openttd.opentttimetables%3Aopenttt-backend&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.openttd.opentttimetables%3Aopenttt-backend)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=org.openttd.opentttimetables%3Aopenttt-backend&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=org.openttd.opentttimetables%3Aopenttt-backend)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=org.openttd.opentttimetables%3Aopenttt-backend&metric=security_rating)](https://sonarcloud.io/dashboard?id=org.openttd.opentttimetables%3Aopenttt-backend)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=org.openttd.opentttimetables%3Aopenttt-backend&metric=sqale_index)](https://sonarcloud.io/dashboard?id=org.openttd.opentttimetables%3Aopenttt-backend)

Backend for OpenTTT, a suite of programs to aid in timetabling [OpenTTD](https://www.openttd.org) trains. 
Yes, we're serious about trains.

## Bird eye view

This is a maven-based Spring Boot application which is meant to run on a Java 11 JVM.

To get it running, the following command should suffice (and add a few sample entries): `SPRING_PROFILES_ACTIVE=dev ./mvnw spring-boot:run`

You can then start to explore the API using the Swagger UI under [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

## Data Model
The core data model is pretty similar to the situation in OpenTTD when entering time tables:

* There are "[`Destination`s](https://github.com/OpenTTT/timetable-api/blob/master/src/main/java/org/openttt/model/Destination.java)", which can be either stations, waypoints or depots (their `DestinationType`). 
  These correspond directly with the orders you can give vehicles in the game, even if you are not timetabling things.
* You can link two or more of these destinations using a [`Timetable`](https://github.com/OpenTTT/timetable-api/blob/master/src/main/java/org/openttt/model/Timetable.java),
  which consists of a number of `TimetabledOrder`s, one for each destinationa vehicle is supposed to go to in the timetable.
* Each of these [`TimetabledOrder`](https://github.com/OpenTTT/timetable-api/blob/master/src/main/java/org/openttt/model/TimetabledOrder.java)
  has a `destination`, `stayingTime` and `travelingTime`. Speaking in OpenTTD terms, these correspond to  in-game timetabled orders as 
  follows: "Go to **destination** and stay for **stayingTime**; Travel for **travelingTime** minutes".
* You can have a [`ScheduledDispatch`](https://github.com/OpenTTT/timetable-api/blob/master/src/main/java/org/openttt/model/ScheduledDispatch.java) 
  for these timetables, which corresponds closely to the interface for scheduled dispatches in-game, with the notable exception that the 
  departures are not measured in actual wall-clock times, but instead in offsets from the start of an interval.
  For example, "Dispatch starts at 16:00, duration 60 minutes, departures at 16:00 and 16:30" corresponds to `durationInMinutes` 
  of 60 minutes, with departures at offsets `0` and `30`.

From these building blocks, the actual time tables are generated:

* A [`Schedule`](https://github.com/OpenTTT/timetable-api/blob/master/src/main/java/org/openttt/model/Schedule.java) is an instance of
  a `Timetable`, based on one of the departures of a `ScheduledDisptach`. Continuing the above example, the 16:00 departure and the
  subsequent stops the vehicle will take is one such schedule.
* This `Schedule` consists of [`ScheduledOrder`s](https://github.com/OpenTTT/timetable-api/blob/master/src/main/java/org/openttt/model/ScheduledOrder.java), 
  which are the arrival and departure times at each point along the orders of the underlying time table. These arrival and departure times
  are just a summation of the staying and travel times of each of the ScheduledOrder's timetable orders. Example: If a timetable has
  two entries which each have 1 minute staying time and 9 minutes traveling time, the first scheduledOrder would have an arrival time of 16:00,
  and departure of `16:01`, the second one an arrival time of `16:10` and a departure time of `16:11`.
  
Each Timetable, and by extension its associated ScheduledDispatch, can have zero or more tags, which are just a visual cue for a crude categorisation
system.

## Scheduling logic
TBD

## Testing
TBD

# OK, cool, so what about it?

The backend powers the [OpenTTT frontend](https://github.com/OpenTTT/frontend), which assists you in timetabling your OpenTTD trains.