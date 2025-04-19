package com.nbtsms.zone_service.service.impl;

import com.nbtsms.zone_service.dto.CenterResponseDTO;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public UUID create(CreateCenterDTO createCenterDTO) throws ConflictException, NotFoundException {
        centerRepository.findByName(createCenterDTO.getName()).ifPresent(center -> {
            throw new ConflictException(Map.of("name", "Center with this name already exists."));
        });

        Region region = regionRepository.findById(createCenterDTO.getRegionId()).orElse(null);

        if (region == null) {
            throw new NotFoundException(Map.of("regionId", "Region not found"));
        }

        Center center = new Center();
        center.setName(createCenterDTO.getName());
        center.setAddress(createCenterDTO.getAddress());
        center.setRegion(region);
        return centerRepository.save(center).getId();
    }

    @Override
    public boolean centerExists(UUID centerId) {
        return centerRepository.findById(centerId).isPresent();
    }

    @Override
    public List<CenterResponseDTO> getCenters() {
        List<Center> centers = centerRepository.findAll();
        return centers.stream()
                .map(CenterMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CenterResponseDTO getCenter(UUID centerId) throws NotFoundException {
        Center center = centerRepository.findById(centerId)
                .orElseThrow(() -> new NotFoundException(Map.of("center", "Center not found")));
        return CenterMapper.toResponse(center);
    }
}
