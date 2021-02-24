package com.decoderapi.service;

import com.decoderapi.domain.SatelliteRequest;
import com.decoderapi.repository.SatelliteRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SatelliteRequestService {

    @Autowired
    private final SatelliteRequestRepository satelliteRequestRepository;

    public List<SatelliteRequest> findAll(){
        return satelliteRequestRepository.findAll();
    }

    public SatelliteRequest findById(Long id){
        return satelliteRequestRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void addRequest(SatelliteRequest request){
        satelliteRequestRepository.save(request);
    }

    public void deleteRequest(Long id){
        satelliteRequestRepository.deleteById(id);
    }

    public Optional<SatelliteRequest> findByName(String name){
        return satelliteRequestRepository.findByName(name);
    }

    public void saveOrUpdate(SatelliteRequest satelliteRequest){
        Optional<SatelliteRequest> request = findByName(satelliteRequest.getName());
        request.ifPresent(req -> deleteRequest(req.getId()));
        addRequest(satelliteRequest);
    }

}
