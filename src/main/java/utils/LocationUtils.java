package utils;

import domain.Coordinates;

public abstract class LocationUtils {
    private static final double EPSILON = 0.000001;


    public static Coordinates calculateTransmitterCoordinates(float satellite0xPosition, float satellite0yPosition, float satellite0distance,
                                                        float satellite1xPosition, float satellite1yPosition, float satellite1distance,
                                                        float satellite2xPosition, float satellite2yPosition, float satellite2distance){
        double p0p2distance, s1s2XDistance, s1s2YDistance, s1s2Distance, p2intersectionDistance, intersection_x, intersection_y;
        double p2_x, p2_y;

        /*vertical and horizontal distances between 2 of the satellites.*/
        s1s2XDistance = satellite1xPosition - satellite0xPosition;
        s1s2YDistance = satellite1yPosition - satellite0yPosition;

        s1s2Distance = getDistance(s1s2XDistance, s1s2YDistance);

        if (s1s2Distance > (satellite0distance + satellite1distance) ||
                s1s2Distance < Math.abs(satellite0distance - satellite1distance)){
            throw new IllegalArgumentException("Can not get valid coordinates from the information received.");
        }

        /* 'point 2' is the point where the line through the circle
         * intersection points crosses the line between the circle
         * centers.
         */

        /* Determine the distance from point 0 to point 2. */
        p0p2distance = (Math.pow(satellite0distance, 2) - Math.pow(satellite1distance, 2) + Math.pow(s1s2Distance,2)) / (2.0 * s1s2Distance);

        /* Determine the coordinates of point 2. */
        p2_x = satellite0xPosition + (s1s2XDistance * p0p2distance/s1s2Distance);
        p2_y = satellite0yPosition + (s1s2YDistance * p0p2distance/s1s2Distance);

        /* Determine the distance from point 2 to either of the
         * intersection points.
         */
        p2intersectionDistance = Math.sqrt(Math.pow(satellite0distance,2) - Math.pow(p0p2distance,2));

        /* Now determine the offsets of the intersection points from point 2.    */
        intersection_x = -s1s2YDistance * (p2intersectionDistance/s1s2Distance);
        intersection_y = s1s2XDistance * (p2intersectionDistance/s1s2Distance);

        /* Determine the absolute intersection points. */
        double intersectionP1_x = p2_x + intersection_x;
        double intersectionP2_x = p2_x - intersection_x;
        double intersectionP1_y = p2_y + intersection_y;
        double intersectionP2_y = p2_y - intersection_y;

        /* Lets determine if circle 3 intersects at either of the above intersection points. */
        s1s2XDistance = intersectionP1_x - satellite2xPosition;
        s1s2YDistance = intersectionP1_y - satellite2yPosition;
        double d1 = getDistance(s1s2XDistance, s1s2YDistance);

        s1s2XDistance = intersectionP2_x - satellite2xPosition;
        s1s2YDistance = intersectionP2_y - satellite2yPosition;
        double d2 = getDistance(s1s2XDistance, s1s2YDistance);

        Coordinates transmitterCoordinates;
        if(Math.abs(d1 - satellite2distance) < EPSILON) {
            transmitterCoordinates = new Coordinates((float) intersectionP1_x, (float) intersectionP1_y);
        }
        else if(Math.abs(d2 - satellite2distance) < EPSILON) {
            transmitterCoordinates = new Coordinates((float)intersectionP2_x, (float)intersectionP2_y);
        }
        else {
            throw new IllegalArgumentException("Can not get valid coordinates from the information received.");
        }
        return transmitterCoordinates;
    }

    private static double getDistance(double xDistance, double yDistance) {
        return Math.sqrt(Math.pow(yDistance, 2) + Math.pow(xDistance, 2));
    }
}
