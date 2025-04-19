package com.nbtsms.zone_service.service;

import com.nbtsms.zone_service.dto.CenterResponseDTO;
import com.nbtsms.zone_service.dto.CreateCenterDTO;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface CenterService {
    UUID create(CreateCenterDTO createCenterDTO) throws ConflictException, NotFoundException;
    boolean centerExists(UUID centerId);
    List<CenterResponseDTO> getCenters();
    CenterResponseDTO getCenter(UUID centerId) throws NotFoundException;
}
