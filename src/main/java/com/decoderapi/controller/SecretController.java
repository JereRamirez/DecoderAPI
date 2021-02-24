package com.decoderapi.controller;

import com.decoderapi.api.Position;
import com.decoderapi.api.SatellitesRequest;
import com.decoderapi.api.TopSecret;
import com.decoderapi.domain.Coordinates;
import com.decoderapi.domain.SatelliteRequest;
import com.decoderapi.service.SatelliteRequestService;
import com.decoderapi.api.SatelliteInfo;
import com.decoderapi.service.LocationService;
import com.decoderapi.service.SatelliteService;
import com.decoderapi.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Slf4j
@RestController
@RequestMapping("/secret_controller")
public class SecretController {

    @Autowired
    private LocationService locationService;
    @Autowired
    private SatelliteService satelliteService;
    @Autowired
    private SatelliteRequestService satelliteRequestService;


    @PostMapping(path = "/topsecret")
    public TopSecret getTopSecret(@RequestBody SatellitesRequest satellitesRequest){

        log.info("Receiving TopSecret request...");
        Map<String, SatelliteInfo> sortedSatellites = satelliteService.sortSatellites(satellitesRequest.getSatellites());

        float[] distances = {sortedSatellites.get("kenobi").getDistance(),
                sortedSatellites.get("skywalker").getDistance(),
                sortedSatellites.get("sato").getDistance()};

        String[][] messages = {sortedSatellites.get("kenobi").getMessage(),
                sortedSatellites.get("skywalker").getMessage(),
                sortedSatellites.get("sato").getMessage()};

        Position position = convertCoordinatesDomainToApi(locationService.getLocation(distances));
        String message = MessageUtils.getMessage(messages);

        if(Objects.equals(message, "invalidString"))
            throw new IllegalArgumentException();

        log.info("Coordinates obtained: {} {}", position.getX(), position.getY());
        log.info("Message decoded: {}", message);

        return new TopSecret(position, message);
    }

    @PostMapping(path = "/topsecret_split/{satellite_name}")
    public void getTopSecretSplit(@PathVariable("satellite_name") String satelliteName, @RequestBody SatelliteInfo satelliteInfo){
        log.info("Receiving TopSecret split request for satellite: {}...", satelliteName);
        SatelliteRequest satelliteRequest = SatelliteRequest.builder()
                .name(satelliteName)
                .distance(satelliteInfo.getDistance())
                .message(Arrays.asList(satelliteInfo.getMessage()))
                .build();
        satelliteRequestService.saveOrUpdate(satelliteRequest);
        log.info("Request saved in the database...");
    }

    @GetMapping(path = "/topsecret_split")
    public TopSecret getTopSecretSplit(){
        log.info("Receiving TopSecret split request for all persisted requests...");

        //I used findAll because I know that there can't be more than one request per satellite
        List<SatelliteRequest> savedRequests = satelliteRequestService.findAll();

        if(!Objects.equals(3, savedRequests.size()))
            throw new IllegalArgumentException("Not enough information to get the message or the coordinates of the ship.");

        return getTopSecret(new SatellitesRequest(infoToRequestConverter(savedRequests)));
    }

    private List<SatelliteInfo> infoToRequestConverter(List<SatelliteRequest> savedRequests) {
        List<SatelliteInfo> satellites = new ArrayList<>();
        for (SatelliteRequest s: savedRequests){
            SatelliteInfo satelliteInfo = new SatelliteInfo();
            satelliteInfo.setName(s.getName());
            satelliteInfo.setDistance(s.getDistance());
            satelliteInfo.setMessage(s.getMessage().toArray(new String[0]));
            satellites.add(satelliteInfo);
        }
        return satellites;
    }


    private Position convertCoordinatesDomainToApi(Coordinates domainCoord) {
        return new Position(domainCoord.getX(), domainCoord.getY());
    }
}
