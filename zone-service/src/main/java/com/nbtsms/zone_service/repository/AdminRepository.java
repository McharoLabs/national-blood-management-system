package com.nbtsms.zone_service.repository;

import com.nbtsms.zone_service.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByAdminId(UUID adminId);
}
