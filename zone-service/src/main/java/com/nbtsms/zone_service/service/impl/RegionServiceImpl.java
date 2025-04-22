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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
        regionRepository.findByName(createRegionDTO.getName()).ifPresent(region -> {
            throw new ConflictException(Map.of("name", "Region with this name already exists."));
        });

        Zone zone = zoneRepository.findById(createRegionDTO.getZoneId()).orElse(null);

        Map<String, String> errors = new HashMap<>();

        if (zone == null) errors.put("zone", "Zone not found");

        if (!errors.isEmpty()) throw new NotFoundException(errors);

        Region region = RegionMapper.toEntity(createRegionDTO);

        zone.getRegions().add(region);
        region.setZone(zone);
        Zone savedZone = zoneRepository.save(zone);

        Region savedRegion = savedZone.getRegions()
                .stream()
                .filter(r -> r.getName().equals(createRegionDTO.getName()))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(Map.of("detail", "Region not saved as expected")));

        return savedRegion.getId();
    }

    @Override
    public boolean regionExists(UUID regionId) {
        return regionRepository.findById(regionId).isPresent();
    }

    @Override
    public List<RegionResponseDTO> getRegions() {
        List<Region> regions = regionRepository.findAll();
        return regions.stream()
                .map(RegionMapper::toResponse)
                .collect(Collectors.toList());
    }

}
