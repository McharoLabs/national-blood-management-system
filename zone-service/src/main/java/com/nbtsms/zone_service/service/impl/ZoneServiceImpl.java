package com.nbtsms.zone_service.service.impl;

import com.nbtsms.zone_service.dto.AssignZoneAdminDTO;
import com.nbtsms.zone_service.dto.CreateZoneDTO;
import com.nbtsms.zone_service.entity.Zone;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;
import com.nbtsms.zone_service.mapper.ZoneMapper;
import com.nbtsms.zone_service.repository.ZoneRepository;
import com.nbtsms.zone_service.service.ZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class ZoneServiceImpl implements ZoneService {
    private static final Logger logger = LoggerFactory.getLogger(ZoneServiceImpl.class);
    private final ZoneRepository zoneRepository;

    public ZoneServiceImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }


    @Override
    public String create(CreateZoneDTO createZoneDTO) throws ConflictException {
        Map<String, String> errors = new HashMap<>();

        Optional<Zone> existingZone = zoneRepository.findByName(createZoneDTO.getName());

        if (existingZone.isPresent()) {
            errors.put("name", "Zone with given name already exists");
            throw new ConflictException(errors);
        }

        try {
            Zone zone = ZoneMapper.toEntity(createZoneDTO);
            zoneRepository.save(zone);
            return "Zone added successfully";
        } catch (DataIntegrityViolationException dv) {
            logger.error("Data integrity violation: zone already exists.", dv);

            String errorMessage = dv.getMessage().toLowerCase();

            if (errorMessage.contains("duplicate key") && errorMessage.contains("name")) {
                errors.put("name", "Zone with given name already exists.");
                throw new ConflictException(errors);
            }

            errors.put("error", "Data integrity violation.");
            throw new ConflictException(errors);
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while creating the zone.", ex);
            throw new RuntimeException(ex.getMessage());
        }
    }


    @Override
    public void assignZoneAdmin(AssignZoneAdminDTO zoneAdminDTO, UUID zoneId) throws NotFoundException {
        Map<String, String> errors = new HashMap<>();

        Zone zone = zoneRepository.findById(zoneId).orElse(null);


        if (zone == null) {
            errors.put("zoneId", "Zone not found with ID: " + zoneId);
            throw new NotFoundException(errors);
        }

        try {
            zone.setAdminId(zoneAdminDTO.getAdminId());
            zoneRepository.save(zone);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while assigning admin to zone.", e);
            throw new RuntimeException(e);
        }
    }



    @Override
    public void removeZoneAdmin(UUID zoneId) throws NotFoundException {
        Zone zone = zoneRepository.findById(zoneId).orElseThrow(() -> {
            Map<String, String> errors = new HashMap<>();
            errors.put("zoneId", "Zone not found with ID: " + zoneId);
            return new NotFoundException(errors);
        });

        zone.setAdminId(null);
        zoneRepository.save(zone);
    }


    @Transactional(readOnly = true)
    @Override
    public boolean isZoneExists(UUID id) {
        return zoneRepository.findById(id).isPresent();
    }
}
