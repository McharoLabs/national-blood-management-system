package com.nbtsms.zone_service.repository;

import com.nbtsms.zone_service.entity.Region;
import com.nbtsms.zone_service.entity.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RegionRepository extends JpaRepository<Region, UUID> {

    @Query("""
            SELECT r FROM Region r
            WHERE LOWER(r.name) = LOWER(:name)
            """)
    Optional<Region> findByNameIgnoreCase(@Param("name") String name);

    @Query("""
            SELECT r FROM Region r
            WHERE r.zone = :zone
            AND LOWER(r.name) LIKE LOWER(CONCAT('%', COALESCE(:name, ''), '%'))
            """)
    Page<Region> findAllByZoneOptionalName(
            @Param("zone") Zone zone,
            @Param("name") String name,
            Pageable pageable
    );

    @Query("""
            SELECT r FROM Region r
            WHERE r.zone.id = :zoneId
            """)
    List<Region> findByZoneId(@Param("zoneId") UUID zoneId);

}
