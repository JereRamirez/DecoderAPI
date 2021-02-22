package com.decoder.controller;

import com.decoder.api.Coordinates;
import com.decoder.api.TopSecret;
import com.decoder.api.SatelliteRequest;
import com.decoder.api.SatellitesRequest;
import com.decoder.service.SatelliteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.decoder.service.LocationService;
import com.decoder.service.MessageService;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/secret_controller")
public class SecretController {

    @Autowired
    private LocationService locationService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private SatelliteService satelliteService;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity processException(){
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/topsecret")
    public TopSecret getTopSecret(@RequestBody SatellitesRequest satellitesRequest){

        log.info("Receiving TopSecret request...");
        HashMap<String, SatelliteRequest> sortedSatellites = satelliteService.sortSatellites(satellitesRequest.getSatellites());
        float[] distances = {getDistanceFrom(sortedSatellites, "kenobi"), getDistanceFrom(sortedSatellites, "skywalker"), getDistanceFrom(sortedSatellites, "sato")};
        String[][] messages = {getMessageFrom(sortedSatellites, "kenobi"), getMessageFrom(sortedSatellites, "skywalker"), getMessageFrom(sortedSatellites, "sato")};

        Coordinates coordinates = convertCoordinatesDomainToApi(locationService.getLocation(distances));
        log.info("Coordinates obtained: {} {}", coordinates.getXPosition(), coordinates.getYPosition());

        String message = messageService.getMessage(messages);
        log.info("Message decoded: {}", message);

        return new TopSecret(coordinates, message);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/topsecret_split/{satellite_name}")
    public void getTopSecretSplit(@PathVariable("satellite_name") String satelliteName, @RequestBody SatelliteRequest satelliteRequest){
        log.info("Receiving TopSecret split request for satellite: {}...", satelliteName);
        satelliteRequest.setName(satelliteName);
        satelliteService.saveRequest(satelliteRequest);
        log.info("Request saved in the database...");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/topsecret_split")
    public void getTopSecretSplit(){
        log.info("Receiving TopSecret split request for all persisted requests...");
        getTopSecret(new SatellitesRequest(satelliteService.findAllSavedRequests()));
    }

    private String[] getMessageFrom(HashMap<String, SatelliteRequest> sortedSatellites, String satellite) {
        return sortedSatellites.get(satellite).getMessage().toArray(new String[0]);
    }

    private float getDistanceFrom(HashMap<String, SatelliteRequest> sortedSatellites, String satellite) {
        return sortedSatellites.get(satellite).getDistance();
    }

    private Coordinates convertCoordinatesDomainToApi(com.decoder.domain.Coordinates domainCoord) {
        return new Coordinates(domainCoord.getXPosition(), domainCoord.getYPosition());
    }
}
