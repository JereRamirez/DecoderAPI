package com.decoderapi.service;

import com.decoderapi.api.SatelliteInfo;
import com.decoderapi.domain.Satellite;
import com.decoderapi.repository.SatelliteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SatelliteService {
    @Autowired
    private final SatelliteRepository satelliteRepository;

    public List<Satellite> findAll(){
        return satelliteRepository.findAll();
    }

    public Satellite findById(Long id){
        return satelliteRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void addSatellite(Satellite satellite){
        satelliteRepository.save(satellite);
    }

    public void addSatellites(Satellite... satellites){
        satelliteRepository.saveAll(Arrays.asList(satellites));
    }

    public void deleteSatellite(Long id){
        satelliteRepository.deleteById(id);
    }

    public Map<String, SatelliteInfo> sortSatellites(List<SatelliteInfo> satellites) {
        log.info("Sorting satellites by defined order.");
        Map<String, SatelliteInfo> satelliteMap = new HashMap<>();

        satellites.forEach(satellite -> {
            if(satellite.getName().equalsIgnoreCase("kenobi")) {
                satelliteMap.put("kenobi", satellite);
            }
            else if(satellite.getName().equalsIgnoreCase("skywalker")) {
                satelliteMap.put("skywalker", satellite);
            }
            else if(satellite.getName().equalsIgnoreCase("sato")) {
                satelliteMap.put("sato", satellite);
            }
        });
        return satelliteMap;
    }
}
