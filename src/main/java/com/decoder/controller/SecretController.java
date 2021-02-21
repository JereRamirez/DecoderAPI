package com.decoder.controller;

import com.decoder.api.Coordinates;
import com.decoder.api.TopSecret;
import com.decoder.api.SatelliteRequest;
import com.decoder.api.SatellitesRequest;
import com.decoder.service.SatelliteService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.decoder.service.LocationService;
import com.decoder.service.MessageService;

import java.util.HashMap;

@RestController
@RequestMapping("/secret_controller")
public class SecretController {

    @Autowired
    private LocationService locationService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private SatelliteService satelliteService;

    private final Logger logger = LoggerFactory.getLogger(SecretController.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity processException(){
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/topsecret")
    public TopSecret getTopSecret(@RequestBody SatellitesRequest satellitesRequest){

        HashMap<String, SatelliteRequest> sortedSatellites = satelliteService.sortSatellites(satellitesRequest.getSatellites());
        float[] distances = {getDistanceFrom(sortedSatellites, "kenobi"), getDistanceFrom(sortedSatellites, "skywalker"), getDistanceFrom(sortedSatellites, "sato")};
        String[][] messages = {getMessageFrom(sortedSatellites, "kenobi"), getMessageFrom(sortedSatellites, "skywalker"), getMessageFrom(sortedSatellites, "sato")};

        Coordinates coordinates = convertCoordinatesDomainToApi(locationService.getLocation(distances));

        String message = messageService.getMessage(messages);

        return new TopSecret(coordinates, message);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/topsecret_split/{satellite_name}")
    public void getTopSecretSplit(@PathVariable("satellite_name") String satelliteName, @RequestBody SatelliteRequest satelliteRequest){
        satelliteRequest.setName(satelliteName);
        satelliteService.saveRequest(satelliteRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/topsecret_split")
    public void getTopSecretSplit(){
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
