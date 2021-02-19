package repository;

import api.SatelliteRequest;
import domain.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SatelliteRequestRepository extends JpaRepository<SatelliteRequest, Long> {
}
