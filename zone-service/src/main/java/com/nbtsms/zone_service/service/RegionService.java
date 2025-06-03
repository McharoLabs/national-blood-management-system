package com.nbtsms.zone_service.service;

import com.nbtsms.zone_service.dto.CreateRegionDTO;
import com.nbtsms.zone_service.dto.RegionResponseDTO;
import com.nbtsms.zone_service.exception.BadRequestException;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RegionService  {
    UUID create(CreateRegionDTO createRegionDTO) throws ConflictException, BadRequestException, NotFoundException;
    boolean regionExists(UUID regionId);
    List<RegionResponseDTO> getRegionsByZone(UUID zoneId);
    Page<RegionResponseDTO> getAllByZone(String name, UUID zoneId, Pageable pageable) throws NotFoundException;
    RegionResponseDTO getRegionById(UUID regionId) throws NotFoundException;
}
