package com.nbtsms.zone_service.service;

import com.nbtsms.zone_service.dto.CenterResponseDTO;
import com.nbtsms.zone_service.dto.CreateCenterDTO;
import com.nbtsms.zone_service.exception.BadRequestException;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CenterService {
    UUID create(CreateCenterDTO createCenterDTO) throws ConflictException, NotFoundException, BadRequestException;
    boolean centerExists(UUID centerId);
    List<CenterResponseDTO> getCentersByRegion(UUID regionId) throws NotFoundException, BadRequestException;
    CenterResponseDTO getCenter(UUID centerId) throws NotFoundException;
    Page<CenterResponseDTO> getAllCenterByZoneId(UUID zoneId, String name, Pageable pageable) throws NotFoundException, BadRequestException;
    Page<CenterResponseDTO> getAllByRegion(String name, UUID regionId, Pageable pageable) throws NotFoundException;
    boolean centerBelongToZone(UUID zoneId, UUID centerId);
}
