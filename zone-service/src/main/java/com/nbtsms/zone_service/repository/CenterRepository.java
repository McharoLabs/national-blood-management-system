package com.nbtsms.zone_service.repository;

import com.nbtsms.zone_service.entity.Center;
import com.nbtsms.zone_service.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CenterRepository extends JpaRepository<Center, UUID> {

    Page<Center> findByRegionZoneId(UUID zoneId, Pageable pageable);

    @Query("""
        SELECT c FROM Center c
        WHERE c.region.zone.id = :zoneId
        AND (LOWER(c.name) LIKE LOWER(CONCAT('%', COALESCE(:name, ''), '%')))
    """)
    Page<Center> findByZoneIdAndOptionalNameContainingIgnoreCase(
            @Param("zoneId") UUID zoneId,
            @Param("name") String name,
            Pageable pageable
    );


    @Query("""
        SELECT c FROM Center c
        WHERE LOWER(c.name) = LOWER(:name)
    """)
    Optional<Center> findByNameIgnoreCase(@Param("name") String name);

    @Query("""
        SELECT c FROM Center c
        WHERE c.region = :region
        AND (LOWER(c.name) LIKE LOWER(CONCAT('%', COALESCE(:name, ''), '%')))
    """)
    Page<Center> findByRegionAndOptionalName(
            @Param("region") Region region,
            @Param("name") String name,
            Pageable pageable
    );

    @Query("""
        SELECT c FROM Center c
        WHERE c.region.id = :regionId
    """)
    List<Center> findAllByRegionId(@Param("regionId") UUID regionId);

}
