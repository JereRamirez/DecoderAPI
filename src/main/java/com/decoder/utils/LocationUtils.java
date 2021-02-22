package com.decoder.utils;


import com.decoder.domain.Coordinates;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class LocationUtils {
    private static final double EPSILON = 1;
    public static final String INVALID_COORDINATES = "Can not get valid coordinates from the information received.";


    public static Coordinates calculateTransmitterCoordinates(Coordinates sat0Coordinates, float satellite0distance,
                                                              Coordinates sat1Coordinates, float satellite1distance,
                                                              Coordinates sat2Coordinates, float satellite2distance){
        log.info("Trying to get a valid coordinates from: ");
        log.info("First satellite located in: X: {} Y: {} with a distance of: {} units.", sat0Coordinates.getXPosition(), sat0Coordinates.getYPosition(), satellite0distance);
        log.info("Second satellite located in: X: {} Y: {} with a distance of: {} units.", sat1Coordinates.getXPosition(), sat1Coordinates.getYPosition(), satellite1distance);
        log.info("Third satellite located in: X: {} Y: {} with a distance of: {} units.", sat2Coordinates.getXPosition(), sat2Coordinates.getYPosition(), satellite2distance);

        double intersectionPointDistance, sat0sat1XDistance, sat0sat1YDistance, sat0sat1Distance, intersectionDistance, intersection_x, intersection_y;
        double intersectionPoint_x, intersectionPoint_y;

        sat0sat1XDistance = sat1Coordinates.getXPosition() - sat0Coordinates.getXPosition();
        sat0sat1YDistance = sat1Coordinates.getYPosition() - sat0Coordinates.getYPosition();

        sat0sat1Distance = getDistance(sat0sat1XDistance, sat0sat1YDistance);

        if (sat0sat1Distance > (satellite0distance + satellite1distance) ||
                sat0sat1Distance < Math.abs(satellite0distance - satellite1distance)){
            log.error(INVALID_COORDINATES);
            throw new IllegalArgumentException(INVALID_COORDINATES);
        }

        intersectionPointDistance = getDistance(satellite0distance, satellite1distance, sat0sat1Distance);

        intersectionPoint_x = sat0Coordinates.getXPosition() + (sat0sat1XDistance * intersectionPointDistance/sat0sat1Distance);
        intersectionPoint_y = sat0Coordinates.getYPosition() + (sat0sat1YDistance * intersectionPointDistance/sat0sat1Distance);

        intersectionDistance = Math.sqrt(Math.pow(satellite0distance,2) - Math.pow(intersectionPointDistance,2));

        intersection_x = -sat0sat1YDistance * (intersectionDistance/sat0sat1Distance);
        intersection_y = sat0sat1XDistance * (intersectionDistance/sat0sat1Distance);

        double intersectionP1_x = intersectionPoint_x + intersection_x;
        double intersectionP1_y = intersectionPoint_y + intersection_y;
        double intersectionP2_x = intersectionPoint_x - intersection_x;
        double intersectionP2_y = intersectionPoint_y - intersection_y;

        log.info("New coordinates obtained with the first two satellites, defining a valid one with the third one.");

        sat0sat1XDistance = intersectionP1_x - sat2Coordinates.getXPosition();
        sat0sat1YDistance = intersectionP1_y - sat2Coordinates.getYPosition();
        double firstDistance = getDistance(sat0sat1XDistance, sat0sat1YDistance);

        sat0sat1XDistance = intersectionP2_x - sat2Coordinates.getXPosition();
        sat0sat1YDistance = intersectionP2_y - sat2Coordinates.getYPosition();
        double secondDistance = getDistance(sat0sat1XDistance, sat0sat1YDistance);

        Coordinates transmitterCoordinates;
        if(Math.abs(firstDistance - satellite2distance) < EPSILON) {
            transmitterCoordinates = new Coordinates((float) intersectionP1_x, (float) intersectionP1_y);
        }
        else if(Math.abs(secondDistance - satellite2distance) < EPSILON) {
            transmitterCoordinates = new Coordinates((float)intersectionP2_x, (float)intersectionP2_y);
        }
        else {
            log.error(INVALID_COORDINATES);
            throw new IllegalArgumentException(INVALID_COORDINATES);
        }
        log.info("Found valid Coordinates of the transmitter...");
        return transmitterCoordinates;
    }

    private static double getDistance(float satellite0distance, float satellite1distance, double sat1sat2Distance) {
        return (Math.pow(satellite0distance, 2) - Math.pow(satellite1distance, 2) + Math.pow(sat1sat2Distance,2)) / (2.0 * sat1sat2Distance);
    }

    private static double getDistance(double xDistance, double yDistance) {
        return Math.sqrt(Math.pow(yDistance, 2) + Math.pow(xDistance, 2));
    }
}
