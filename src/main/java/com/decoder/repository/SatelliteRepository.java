package com.decoder.repository;

import com.decoder.domain.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SatelliteRepository extends JpaRepository<Satellite, String> {
}
