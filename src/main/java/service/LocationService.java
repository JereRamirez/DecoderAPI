package service;

import domain.Coordinates;
import domain.Satellite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utils.LocationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LocationService {
    private List<Satellite> satellites;

    public LocationService() {
        this.satellites = new ArrayList<>();
    }

    public Coordinates getLocation(float... distances) {
        Coordinates firstCoordinates = satellites.get(0).getCoordinates();
        Coordinates secondCoordinates = satellites.get(1).getCoordinates();
        Coordinates thirdCoordinates = satellites.get(2).getCoordinates();

        return LocationUtils.calculateTransmitterCoordinates(firstCoordinates.getXPosition(),
                firstCoordinates.getYPosition(), distances[0],
                secondCoordinates.getXPosition(),
                secondCoordinates.getYPosition(), distances[1],
                thirdCoordinates.getXPosition(),
                thirdCoordinates.getYPosition(), distances[2]);

    }

    public void addSatellites(Satellite... newSatellites){
        for(Satellite sat: newSatellites){
           satellites.add(sat);
        }
    }
}
