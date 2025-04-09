package com.nbtsms.zone_service.service;

import com.nbtsms.zone_service.dto.AssignZoneAdminDTO;
import com.nbtsms.zone_service.dto.CreateZoneDTO;
import com.nbtsms.zone_service.entity.Zone;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ZoneService {
    void create(CreateZoneDTO createZoneDTO) throws ConflictException, ConflictException;
    void assignZoneAdmin(AssignZoneAdminDTO zoneAdminDTO, UUID zoneId) throws NotFoundException, ConflictException;
    void removeZoneAdmin(UUID zoneId) throws NotFoundException;
    boolean isZoneExists(UUID id);
}
