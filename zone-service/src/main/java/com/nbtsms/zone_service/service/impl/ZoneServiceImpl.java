package com.nbtsms.zone_service.service.impl;

import com.nbtsms.zone_service.dto.AdminResponseDTO;
import com.nbtsms.zone_service.dto.CreateZoneDTO;
import com.nbtsms.zone_service.dto.ZoneResponseDTO;
import com.nbtsms.zone_service.entity.Zone;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;
import com.nbtsms.zone_service.mapper.AdminMapper;
import com.nbtsms.zone_service.mapper.ZoneMapper;
import com.nbtsms.zone_service.repository.AdminRepository;
import com.nbtsms.zone_service.repository.ZoneRepository;
import com.nbtsms.zone_service.service.ZoneService;
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
public class ZoneServiceImpl implements ZoneService {
    private static final Logger logger = LoggerFactory.getLogger(ZoneServiceImpl.class);
    private final ZoneRepository zoneRepository;
    private final AdminRepository adminRepository;

    public ZoneServiceImpl(ZoneRepository zoneRepository, AdminRepository adminRepository) {
        this.zoneRepository = zoneRepository;
        this.adminRepository = adminRepository;
    }


    @Override
    public UUID addZone(CreateZoneDTO createZoneDTO) throws ConflictException {
        zoneRepository.findByName(createZoneDTO.getName()).ifPresent(z -> {
            throw new ConflictException(Map.of("name", "Zone with this name already exists."));
        });

        Zone zone = ZoneMapper.toEntity(createZoneDTO);
        UUID id = zoneRepository.save(zone).getId();

        logger.info("Zone added successfully: {}", zone);
        return id;
    }


    @Transactional(readOnly = true)
    @Override
    public boolean zoneExists(UUID id) {
        return zoneRepository.findById(id).isPresent();
    }

    @Override
    public ZoneResponseDTO getZone(UUID id) throws NotFoundException {
        Zone zone = zoneRepository.findById(id).orElseThrow(() -> new NotFoundException(Map.of("detail", "Zone not found")));
        return ZoneMapper.toResponse(zone);
    }

    @Override
    public List<ZoneResponseDTO> getZones() {
        List<Zone> zones = zoneRepository.findAll();

        return zones
                .stream()
                .map(ZoneMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UUID getZoneIdById(UUID zoneId) {
        return zoneRepository.findById(zoneId)
                .map(Zone::getId)
                .orElse(null);
    }

    @Override
    public Page<AdminResponseDTO> zoneAdmins(UUID zoneId, String name, Pageable pageable) throws NotFoundException {
        return adminRepository.findAdminsByZoneIdAndName(zoneId, name, pageable)
                .map(AdminMapper::toResponse);
    }
}
