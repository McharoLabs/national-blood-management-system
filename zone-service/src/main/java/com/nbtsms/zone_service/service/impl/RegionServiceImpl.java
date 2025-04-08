package com.nbtsms.zone_service.service.impl;

import com.nbtsms.zone_service.dto.CreateRegionDTO;
import com.nbtsms.zone_service.entity.Region;
import com.nbtsms.zone_service.entity.Zone;
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
import java.util.Map;

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
    public void create(CreateRegionDTO createRegionDTO) throws ConflictException, NotFoundException {
        Map<String, String> errors = new HashMap<>();

        try {
            Zone zone = zoneRepository.findById(createRegionDTO.getZoneId()).orElse(null);

            if (zone == null) {
                errors.put("zoneId", "Zone not found");
                throw new NotFoundException(errors);
            }

            Region region = RegionMapper.toEntity(createRegionDTO);
            region.setZone(zone);
            regionRepository.save(region);
        } catch (DataIntegrityViolationException dv) {
            logger.error("Region already exists or data integrity violation.", dv);

            String errorMessage = dv.getMessage().toLowerCase();

            if (errorMessage.contains("duplicate key")) {
                if (errorMessage.contains("name")) {
                    errors.put("name", "Region already exists.");
                }
            }

            if (!errors.isEmpty()) {
                throw new ConflictException(errors);
            }

            throw new ConflictException(Map.of("error", "Data integrity violation."));
        }
        catch (RuntimeException ex) {
            logger.error("An unexpected error occurred while creating the region.", ex);
            throw ex;
        }
    }

}
