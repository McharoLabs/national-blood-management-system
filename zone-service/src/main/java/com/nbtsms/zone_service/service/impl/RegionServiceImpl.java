package com.nbtsms.zone_service.service.impl;

import com.nbtsms.zone_service.dto.CreateRegionDTO;
import com.nbtsms.zone_service.dto.RegionResponseDTO;
import com.nbtsms.zone_service.entity.Region;
import com.nbtsms.zone_service.entity.Zone;
import com.nbtsms.zone_service.exception.BadRequestException;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;
import com.nbtsms.zone_service.mapper.RegionMapper;
import com.nbtsms.zone_service.repository.RegionRepository;
import com.nbtsms.zone_service.repository.ZoneRepository;
import com.nbtsms.zone_service.service.RegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional(isolation = Isolation.SERIALIZABLE)
@Service
public class RegionServiceImpl implements RegionService {
    private static final Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);
    private final RegionRepository regionRepository;
    private final ZoneRepository zoneRepository;

    public RegionServiceImpl(RegionRepository regionRepository,  ZoneRepository zoneRepository) {
        this.regionRepository = regionRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public UUID create(CreateRegionDTO createRegionDTO) throws ConflictException, NotFoundException, BadRequestException {
        regionRepository.findByNameIgnoreCase(createRegionDTO.getName()).ifPresent(region -> {
            throw new ConflictException(Map.of("name", "Region with this name already exists."));
        });

        Zone zone = zoneRepository.findById(createRegionDTO.getZoneId())
                .orElseThrow(() -> new NotFoundException(Map.of("zoneId", "Zone not found")));

        Region region = RegionMapper.toEntity(createRegionDTO);

        zone.getRegions().add(region);
        region.setZone(zone);
        Zone savedZone = zoneRepository.save(zone);

        Region savedRegion = savedZone.getRegions()
                .stream()
                .filter(r -> r.getName().equals(createRegionDTO.getName()))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(Map.of("detail", "Region not saved as expected")));

        logger.info("Region added to zone successful: {}", region);

        return savedRegion.getId();
    }

    @Override
    public boolean regionExists(UUID regionId) {
        return regionRepository.findById(regionId).isPresent();
    }

    @Override
    public List<RegionResponseDTO> getRegionsByZone(UUID zoneId) {
        return regionRepository.findByZoneId(zoneId)
                .stream()
                .map(RegionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<RegionResponseDTO> getAllByZone(String name, UUID zoneId, Pageable pageable) throws NotFoundException {
        Zone zone = zoneRepository.findById(zoneId).orElseThrow(() -> new NotFoundException(Map.of("detail", "Zone not found")));

        Page<Region> allByZoneOptionalName = regionRepository.findAllByZoneOptionalName(zone, name, pageable);
        return allByZoneOptionalName.map(RegionMapper::toResponse);
    }

    @Override
    public RegionResponseDTO getRegionById(UUID regionId) throws NotFoundException {
        return RegionMapper
                .toResponse(
                        regionRepository.findById(regionId)
                                .orElseThrow(() -> new NotFoundException(Map.of("detail", "Region not found")))
                );
    }


}
