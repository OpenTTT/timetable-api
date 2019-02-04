<p align="center">
    <img src="https://raw.githubusercontent.com/opentttimetables/opentttimetables/master/media/openTTT.png" alt="OpenTTT logo">
    <h1>Timetabling API</h1>
</p>

[![Build Status](https://travis-ci.org/opentttimetables/timetable-api.svg?branch=master)](https://travis-ci.org/opentttimetables/timetable-api)

Backend for OpenTTD, a suite of programs to aid in timetabling [OpenTTD](https://www.openttd.org) trains. 
Yes, we're serious about trains.

## Bird eye view

This is a maven-based Spring Boot application which is meant to run on a Java 11 JVM.

To get it running, the following command should suffice (and add a few sample entries): `SPRING_PROFILES_ACTIVE=dev ./mvnw spring-boot:run`

You can then start to explore the API...
```bash
curl localhost:8080/destinations/
(a nice set of stations, waypoints and depots)
```

## Endpoints

TBD -> Generating a SwaggerUI is a plan, so this section could just tell you to check it out?

## Data Model
TBD

## Scheduling logic
TBD

## Testing
TBD