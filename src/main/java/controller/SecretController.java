package controller;

import api.SatelliteRequest;
import api.SatellitesRequest;
import api.TopSecret;
import domain.Coordinates;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.LocationService;
import service.MessageService;
import service.SatelliteService;

import java.util.HashMap;

@RestController
@RequestMapping("/secret_controller")
@RequiredArgsConstructor
public class SecretController {

    private final LocationService locationService;
    private final MessageService messageService;
    private final SatelliteService satelliteService;

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

        Coordinates coordinates = locationService.getLocation(distances);
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
        return sortedSatellites.get(satellite).getMessage();
    }

    private float getDistanceFrom(HashMap<String, SatelliteRequest> sortedSatellites, String satellite) {
        return sortedSatellites.get(satellite).getDistance();
    }
}
