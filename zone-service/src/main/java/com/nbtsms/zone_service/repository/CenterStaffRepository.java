package com.nbtsms.zone_service.repository;

import com.nbtsms.zone_service.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CenterStaffRepository extends JpaRepository<Staff, UUID> {
    Optional<Staff> findByStaffId(UUID staffId);
}
