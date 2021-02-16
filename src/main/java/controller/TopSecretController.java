package controller;

import api.SatellitesRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class TopSecretController {

    @PostMapping(path = "/topsecret")
    public void getTopSecret(@RequestBody SatellitesRequest satelliteRequest){

    }
}
