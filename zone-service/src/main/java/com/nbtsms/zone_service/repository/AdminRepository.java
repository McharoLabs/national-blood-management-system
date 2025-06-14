package com.nbtsms.zone_service.repository;

import com.nbtsms.zone_service.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByAdminId(UUID adminId);

    @Query("""
        SELECT a FROM Admin a
        WHERE a.zone.id = :zoneId
        AND LOWER(CONCAT(a.firstName, ' ', COALESCE(a.middleName, ''), ' ', a.lastName))
            LIKE LOWER(CONCAT('%', COALESCE(:name, ''), '%'))
    """)
    Page<Admin> findAdminsByZoneIdAndName(
            @Param("zoneId") UUID zoneId,
            @Param("name") String name,
            Pageable pageable
    );
}
