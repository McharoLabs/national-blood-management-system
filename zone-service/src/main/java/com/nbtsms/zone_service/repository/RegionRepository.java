package com.nbtsms.zone_service.repository;

import com.nbtsms.zone_service.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegionRepository extends JpaRepository<Region, UUID> {
}
