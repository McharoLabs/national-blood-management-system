package com.nbtsms.zone_service.service;

import com.nbtsms.zone_service.dto.CreateRegionDTO;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;

public interface RegionService  {
    void create(CreateRegionDTO createRegionDTO) throws ConflictException, NotFoundException;
}
