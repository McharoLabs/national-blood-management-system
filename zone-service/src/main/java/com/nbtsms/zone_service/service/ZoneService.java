package com.nbtsms.zone_service.service;

import com.nbtsms.zone_service.dto.AdminResponseDTO;
import com.nbtsms.zone_service.dto.CreateZoneDTO;
import com.nbtsms.zone_service.dto.ZoneResponseDTO;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ZoneService {
    UUID addZone(CreateZoneDTO createZoneDTO) throws ConflictException;
    boolean zoneExists(UUID id);
    ZoneResponseDTO getZone(UUID id) throws NotFoundException;
    List<ZoneResponseDTO> getZones();
    UUID getZoneIdById(UUID zoneId);
    Page<AdminResponseDTO> zoneAdmins(UUID zoneId, String name, Pageable pageable) throws NotFoundException;
}
