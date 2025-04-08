package com.nbtsms.zone_service.service.impl;

import com.nbtsms.zone_service.dto.CreateCenterDTO;
import com.nbtsms.zone_service.entity.Center;
import com.nbtsms.zone_service.entity.Region;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;
import com.nbtsms.zone_service.mapper.CenterMapper;
import com.nbtsms.zone_service.repository.CenterRepository;
import com.nbtsms.zone_service.repository.RegionRepository;
import com.nbtsms.zone_service.service.CenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CenterServiceImpl implements CenterService {
    private static final Logger logger = LoggerFactory.getLogger(CenterServiceImpl.class);
    private final CenterRepository centerRepository;
    private final RegionRepository regionRepository;

    public CenterServiceImpl(CenterRepository centerRepository, RegionRepository regionRepository) {
        this.centerRepository = centerRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public void create(CreateCenterDTO createCenterDTO) throws ConflictException, NotFoundException {
        Map<String, String> errors = new HashMap<>();

        Region region = regionRepository.findById(createCenterDTO.getRegionId()).orElse(null);
        if (region == null) {
            errors.put("regionId", "Region not found");
            throw new NotFoundException(errors);
        }

        Center center = CenterMapper.toEntity(createCenterDTO);
        center.setRegion(region);

        try {
            centerRepository.save(center);
        } catch (DataIntegrityViolationException dv) {
            logger.error("Center already exists or data integrity violation.", dv);

            String errorMessage = dv.getMessage().toLowerCase();

            if (errorMessage.contains("duplicate key")) {
                if (errorMessage.contains("name")) {
                    errors.put("name", "Center with this name already exists.");
                }
            }

            if (!errors.isEmpty()) {
                throw new ConflictException(errors);
            }

            throw new ConflictException(Map.of("error", "Data integrity violation."));
        } catch (RuntimeException ex) {
            logger.error("An unexpected error occurred while creating the center.", ex);
            throw ex;
        }
    }
}
