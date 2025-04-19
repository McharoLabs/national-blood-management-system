package com.nbtsms.zone_service.service;

import com.nbtsms.zone_service.dto.CreateRegionDTO;
import com.nbtsms.zone_service.dto.RegionResponseDTO;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface RegionService  {
    UUID create(CreateRegionDTO createRegionDTO) throws ConflictException, NotFoundException;
    boolean regionExists(UUID regionId);
    List<RegionResponseDTO> getRegions();
}
