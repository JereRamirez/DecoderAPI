package service;

import domain.Satellite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.SatelliteRepository;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SatelliteService {
    @Autowired
    SatelliteRepository satelliteRepository;

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
}
