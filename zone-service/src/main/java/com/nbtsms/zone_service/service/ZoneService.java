package com.nbtsms.zone_service.service;

import com.nbtsms.zone_service.dto.CreateZoneDTO;
import com.nbtsms.zone_service.dto.ZoneResponseDTO;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface ZoneService {
    UUID addZone(CreateZoneDTO createZoneDTO) throws ConflictException, ConflictException;
    boolean zoneExists(UUID id);
    ZoneResponseDTO getZone(UUID id) throws NotFoundException;
    List<ZoneResponseDTO> getZones();
}
