package com.decoderapi.repository;


import com.decoderapi.domain.SatelliteRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SatelliteRequestRepository extends JpaRepository<SatelliteRequest, Long> {
    Optional<SatelliteRequest> findByName(String name);
}
