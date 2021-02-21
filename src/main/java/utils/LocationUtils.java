package utils;


import domain.Coordinates;

public abstract class LocationUtils {
    private static final double EPSILON = 1;


    public static Coordinates calculateTransmitterCoordinates(Coordinates sat0Coordinates, float satellite0distance,
                                                              Coordinates sat1Coordinates, float satellite1distance,
                                                              Coordinates sat2Coordinates, float satellite2distance){

        double intersectionPointDistance, sat1sat2XDistance, sat1sat2YDistance, sat1sat2Distance, intersectionDistance, intersection_x, intersection_y;
        double intersectionPoint_x, intersectionPoint_y;

        sat1sat2XDistance = sat1Coordinates.getXPosition() - sat0Coordinates.getXPosition();
        sat1sat2YDistance = sat1Coordinates.getYPosition() - sat0Coordinates.getYPosition();

        sat1sat2Distance = getDistance(sat1sat2XDistance, sat1sat2YDistance);

        if (sat1sat2Distance > (satellite0distance + satellite1distance) ||
                sat1sat2Distance < Math.abs(satellite0distance - satellite1distance)){
            throw new IllegalArgumentException("Can not get valid coordinates from the information received.");
        }

        intersectionPointDistance = getDistance(satellite0distance, satellite1distance, sat1sat2Distance);

        intersectionPoint_x = sat0Coordinates.getXPosition() + (sat1sat2XDistance * intersectionPointDistance/sat1sat2Distance);
        intersectionPoint_y = sat0Coordinates.getYPosition() + (sat1sat2YDistance * intersectionPointDistance/sat1sat2Distance);

        intersectionDistance = Math.sqrt(Math.pow(satellite0distance,2) - Math.pow(intersectionPointDistance,2));

        intersection_x = -sat1sat2YDistance * (intersectionDistance/sat1sat2Distance);
        intersection_y = sat1sat2XDistance * (intersectionDistance/sat1sat2Distance);

        double intersectionP1_x = intersectionPoint_x + intersection_x;
        double intersectionP1_y = intersectionPoint_y + intersection_y;
        double intersectionP2_x = intersectionPoint_x - intersection_x;
        double intersectionP2_y = intersectionPoint_y - intersection_y;

        sat1sat2XDistance = intersectionP1_x - sat2Coordinates.getXPosition();
        sat1sat2YDistance = intersectionP1_y - sat2Coordinates.getYPosition();
        double firstDistance = getDistance(sat1sat2XDistance, sat1sat2YDistance);

        sat1sat2XDistance = intersectionP2_x - sat2Coordinates.getXPosition();
        sat1sat2YDistance = intersectionP2_y - sat2Coordinates.getYPosition();
        double secondDistance = getDistance(sat1sat2XDistance, sat1sat2YDistance);

        Coordinates transmitterCoordinates;
        if(Math.abs(firstDistance - satellite2distance) < EPSILON) {
            transmitterCoordinates = new Coordinates((float) intersectionP1_x, (float) intersectionP1_y);
        }
        else if(Math.abs(secondDistance - satellite2distance) < EPSILON) {
            transmitterCoordinates = new Coordinates((float)intersectionP2_x, (float)intersectionP2_y);
        }
        else {
            throw new IllegalArgumentException("Can not get valid coordinates from the information received.");
        }
        return transmitterCoordinates;
    }

    private static double getDistance(float satellite0distance, float satellite1distance, double sat1sat2Distance) {
        return (Math.pow(satellite0distance, 2) - Math.pow(satellite1distance, 2) + Math.pow(sat1sat2Distance,2)) / (2.0 * sat1sat2Distance);
    }

    private static double getDistance(double xDistance, double yDistance) {
        return Math.sqrt(Math.pow(yDistance, 2) + Math.pow(xDistance, 2));
    }
}
