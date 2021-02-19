package service;

import api.SatelliteRequest;
import domain.Satellite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SatelliteRepository;
import repository.SatelliteRequestRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SatelliteService {
    @Autowired
    SatelliteRepository satelliteRepository;

    @Autowired
    SatelliteRequestRepository satelliteRequestRepository;

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
