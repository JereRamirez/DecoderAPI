package com.decoderapi.utils;


import com.decoderapi.domain.Coordinates;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class LocationUtils {
    private static final double EPSILON = 1;
    public static final String INVALID_COORDINATES = "Can not get valid coordinates from the information received.";


    public static Coordinates calculateTransmitterCoordinates(Coordinates sat0Coordinates, float satellite0distance,
                                                              Coordinates sat1Coordinates, float satellite1distance,
                                                              Coordinates sat2Coordinates, float satellite2distance){
        log.info("Trying to get a valid coordinates from: ");
        log.info("First satellite located in: X: {} Y: {} with a distance of: {} units.", sat0Coordinates.getX(), sat0Coordinates.getY(), satellite0distance);
        log.info("Second satellite located in: X: {} Y: {} with a distance of: {} units.", sat1Coordinates.getX(), sat1Coordinates.getY(), satellite1distance);
        log.info("Third satellite located in: X: {} Y: {} with a distance of: {} units.", sat2Coordinates.getX(), sat2Coordinates.getY(), satellite2distance);

        double intersectionPointDistance, sat0sat1XDistance, sat0sat1YDistance, sat0sat1Distance, intersectionDistance, intersectionX, intersectionY;
        double intersectionPointX, intersectionPointY;

        /*
            In this block of code I try to get the intersection between the first two circles.
         */

        sat0sat1XDistance = sat1Coordinates.getX() - sat0Coordinates.getX();
        sat0sat1YDistance = sat1Coordinates.getY() - sat0Coordinates.getY();

        sat0sat1Distance = getDistance(sat0sat1XDistance, sat0sat1YDistance);

        if (sat0sat1Distance > (satellite0distance + satellite1distance) ||
                sat0sat1Distance < Math.abs(satellite0distance - satellite1distance)){
            log.error(INVALID_COORDINATES);
            throw new IllegalArgumentException(INVALID_COORDINATES);
        }

        intersectionPointDistance = getDistance(satellite0distance, satellite1distance, sat0sat1Distance);

        intersectionPointX = sat0Coordinates.getX() + (sat0sat1XDistance * intersectionPointDistance/sat0sat1Distance);
        intersectionPointY = sat0Coordinates.getY() + (sat0sat1YDistance * intersectionPointDistance/sat0sat1Distance);

        intersectionDistance = Math.sqrt(Math.pow(satellite0distance,2) - Math.pow(intersectionPointDistance,2));

        intersectionX = -sat0sat1YDistance * (intersectionDistance/sat0sat1Distance);
        intersectionY = sat0sat1XDistance * (intersectionDistance/sat0sat1Distance);

        double intersectionP1X = intersectionPointX + intersectionX;
        double intersectionP1Y = intersectionPointY + intersectionY;
        double intersectionP2X = intersectionPointX - intersectionX;
        double intersectionP2Y = intersectionPointY - intersectionY;

        log.info("New coordinates obtained with the first two satellites, defining a valid one with the third one.");

        /*
            In this block of code I try to get the intersection of the first two circles with the third one.
         */

        sat0sat1XDistance = intersectionP1X - sat2Coordinates.getX();
        sat0sat1YDistance = intersectionP1Y - sat2Coordinates.getY();
        double firstDistance = getDistance(sat0sat1XDistance, sat0sat1YDistance);

        sat0sat1XDistance = intersectionP2X - sat2Coordinates.getX();
        sat0sat1YDistance = intersectionP2Y - sat2Coordinates.getY();
        double secondDistance = getDistance(sat0sat1XDistance, sat0sat1YDistance);

        /*
            In this block of code I build the transmitter's coordinates in case it's possible.
         */

        Coordinates transmitterCoordinates;
        if(Math.abs(firstDistance - satellite2distance) < EPSILON) {
            transmitterCoordinates = new Coordinates((float) intersectionP1X, (float) intersectionP1Y);
        }
        else if(Math.abs(secondDistance - satellite2distance) < EPSILON) {
            transmitterCoordinates = new Coordinates((float)intersectionP2X, (float)intersectionP2Y);
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
