package com.decoder.repository;

import com.decoder.api.SatelliteRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SatelliteRequestRepository extends JpaRepository<SatelliteRequest, String> {
}
