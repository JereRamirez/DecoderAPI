package com.decoder.service;

import com.decoder.api.SatelliteRequest;
import com.decoder.domain.Satellite;
import com.decoder.repository.SatelliteRepository;
import com.decoder.repository.SatelliteRequestRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SatelliteService {
    @Autowired
    private final SatelliteRepository satelliteRepository;

    @Autowired
    private final SatelliteRequestRepository satelliteRequestRepository;

    private final Logger logger = LoggerFactory.getLogger(SatelliteService.class);

    public List<Satellite> findAll(){
        return satelliteRepository.findAll();
    }

    public Satellite findById(String id){
        return satelliteRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void addSatellite(Satellite satellite){
        satelliteRepository.save(satellite);
    }

    public void addSatellites(Satellite... satellites){
        satelliteRepository.saveAll(Arrays.asList(satellites));
    }

    public void deleteSatellite(String id){
        satelliteRepository.deleteById(id);
    }

    public HashMap<String, SatelliteRequest> sortSatellites(List<SatelliteRequest> satellites) {
        HashMap<String, SatelliteRequest> satelliteMap = new HashMap<>();
        satellites.forEach(satellite -> {
            decideSatelliteName(satelliteMap, satellite);
        });
        return satelliteMap;
    }

    public void saveRequest(SatelliteRequest satelliteRequest) {
        satelliteRequestRepository.save(satelliteRequest);
    }

    public List<SatelliteRequest> findAllSavedRequests(){ return satelliteRequestRepository.findAll();  }

    private void decideSatelliteName(HashMap<String, SatelliteRequest> satelliteMap, SatelliteRequest satellite) {
        if(isNamed(satellite, "kenobi"))
            satelliteMap.put("kenobi", satellite);
        else if(isNamed(satellite, "skywalker"))
            satelliteMap.put("skywalker", satellite);
        else if(isNamed(satellite, "sato"))
            satelliteMap.put("sato", satellite);
    }

    private boolean isNamed(SatelliteRequest satellite, String name) {
        return satellite.getName().equalsIgnoreCase(name);
    }
}
