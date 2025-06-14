package com.nbtsms.zone_service.service.impl;

import com.nbtsms.zone_service.dto.CenterResponseDTO;
import com.nbtsms.zone_service.dto.CreateCenterDTO;
import com.nbtsms.zone_service.entity.Center;
import com.nbtsms.zone_service.entity.Region;
import com.nbtsms.zone_service.entity.Zone;
import com.nbtsms.zone_service.exception.BadRequestException;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;
import com.nbtsms.zone_service.mapper.CenterMapper;
import com.nbtsms.zone_service.repository.CenterRepository;
import com.nbtsms.zone_service.repository.RegionRepository;
import com.nbtsms.zone_service.repository.ZoneRepository;
import com.nbtsms.zone_service.service.CenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional(isolation = Isolation.SERIALIZABLE)
@Service
public class CenterServiceImpl implements CenterService {
    private static final Logger logger = LoggerFactory.getLogger(CenterServiceImpl.class);
    private final CenterRepository centerRepository;
    private final RegionRepository regionRepository;
    private final ZoneRepository zoneRepository;

    public CenterServiceImpl(CenterRepository centerRepository, RegionRepository regionRepository,
                             ZoneRepository zoneRepository) {
        this.centerRepository = centerRepository;
        this.regionRepository = regionRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public UUID create(CreateCenterDTO createCenterDTO) throws ConflictException, NotFoundException, BadRequestException {
        centerRepository.findByNameIgnoreCase(createCenterDTO.getName()).ifPresent(center -> {
            throw new ConflictException(Map.of("name", "Center with this name already exists."));
        });

        Region region = regionRepository.findById(createCenterDTO.getRegionId())
                .orElseThrow(() -> new NotFoundException(Map.of("regionId", "Region not found")));

        Center center = CenterMapper.toEntity(createCenterDTO);
        center.setRegion(region);

        region.getCenters().add(center);
        Region savedRegion = regionRepository.save(region);

        Center savedCenter = savedRegion.getCenters()
                        .stream()
                                .filter(c -> c.getName().equals(createCenterDTO.getName()))
                                        .findFirst()
                                                .orElseThrow(() -> new BadRequestException(Map.of("detail", "Center not saved as expected")));

        logger.info("Center added successful to the region: {}", center);

        return savedCenter.getId();
    }

    @Override
    public boolean centerExists(UUID centerId) {
        return centerRepository.findById(centerId).isPresent();
    }

    @Override
    public List<CenterResponseDTO> getCentersByRegion(UUID regionId) throws NotFoundException, BadRequestException {

        return centerRepository.findAllByRegionId(regionId)
                .stream()
                .map(CenterMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CenterResponseDTO getCenter(UUID centerId) throws NotFoundException {
        Center center = centerRepository.findById(centerId)
                .orElseThrow(() -> new NotFoundException(Map.of("center", "Center not found")));
        return CenterMapper.toResponse(center);
    }

    @Override
    public Page<CenterResponseDTO> getAllCenterByZoneId(UUID zoneId, String name, Pageable pageable) throws NotFoundException, BadRequestException {


        Zone zone = zoneRepository.findById(zoneId).orElseThrow(() -> new NotFoundException(Map.of("detail", "Zone you are assigned to, not found")));

        return centerRepository.findByZoneIdAndOptionalNameContainingIgnoreCase(zone.getId(), name, pageable)
                .map(CenterMapper::toResponse);
    }

    @Override
    public Page<CenterResponseDTO> getAllByRegion(String name, UUID regionId, Pageable pageable) throws NotFoundException {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new NotFoundException(Map.of("detail", "Region not found")));

        return centerRepository.findByRegionAndOptionalName(region, name, pageable)
                .map(CenterMapper::toResponse);
    }

    @Override
    public boolean centerBelongToZone(UUID zoneId, UUID centerId) {
        Zone zone = zoneRepository.findById(zoneId).orElse(null);

        if (zone == null) {
            return false;
        }

        Set<Region> regions = zone.getRegions();

        return regions.stream()
                .flatMap(region -> region.getCenters().stream())
                .anyMatch(center -> center.getId().equals(centerId));
    }

}
