package com.nbtsms.zone_service.repository;

import com.nbtsms.zone_service.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ZoneRepository extends JpaRepository<Zone, UUID> {
    Optional<Zone> findByName(String name);
}
