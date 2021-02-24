package com.decoderapi.service;

import com.decoderapi.domain.Coordinates;
import com.decoderapi.domain.Satellite;
import com.decoderapi.utils.LocationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    @Autowired
    private final SatelliteService satelliteService;

    public Coordinates getLocation(float... distances) {
        if(Objects.isNull(distances))
            throw new IllegalArgumentException("The distances between the satellites can not be empty.");

        if(!Objects.equals(distances.length, 3))
            throw new IllegalArgumentException("The amount of distances must be three.");

        log.info("Starting location service...");
        //I use findAll because I know there can only be three satellites
        List<Satellite> satellites = satelliteService.findAll();

        Coordinates firstCoordinates = satellites.get(0).getCoordinates();
        Coordinates secondCoordinates = satellites.get(1).getCoordinates();
        Coordinates thirdCoordinates = satellites.get(2).getCoordinates();

        return LocationUtils.calculateTransmitterCoordinates(firstCoordinates, distances[0],
                secondCoordinates, distances[1],
                thirdCoordinates, distances[2]);

    }

}
