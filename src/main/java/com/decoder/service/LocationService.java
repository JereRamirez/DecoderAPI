package com.decoder.service;

import com.decoder.domain.Coordinates;
import com.decoder.domain.Satellite;
import com.decoder.utils.LocationUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    @Autowired
    private final SatelliteService satelliteService;

    public Coordinates getLocation(float... distances) {
        log.info("Starting location service...");
        List<Satellite> satellites = satelliteService.findAll();

        Coordinates firstCoordinates = satellites.get(0).getCoordinates();
        Coordinates secondCoordinates = satellites.get(1).getCoordinates();
        Coordinates thirdCoordinates = satellites.get(2).getCoordinates();

        return LocationUtils.calculateTransmitterCoordinates(firstCoordinates, distances[0],
                secondCoordinates, distances[1],
                thirdCoordinates, distances[2]);

    }

}
