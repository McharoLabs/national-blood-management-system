package com.nbtsms.zone_service.repository;

import com.nbtsms.zone_service.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CenterRepository extends JpaRepository<Center, UUID> {
}
