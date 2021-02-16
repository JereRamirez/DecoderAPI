package controller;

import api.SatelliteRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class TopSecretController {

    @PostMapping(path = "/topsecret")
    public void getTopSecret(@RequestBody SatelliteRequest satelliteRequest){

    }
}
