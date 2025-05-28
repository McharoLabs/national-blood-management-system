package com.nbtsms.zone_service.service.impl;

import com.nbtsms.zone_service.entity.Admin;
import com.nbtsms.zone_service.entity.Zone;
import com.nbtsms.zone_service.event.AdminZoneAssignmentEvent;
import com.nbtsms.zone_service.event.AdminZoneUnassignmentEvent;
import com.nbtsms.zone_service.mapper.AdminMapper;
import com.nbtsms.zone_service.repository.AdminRepository;
import com.nbtsms.zone_service.repository.ZoneRepository;
import com.nbtsms.zone_service.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(isolation = Isolation.SERIALIZABLE)
@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final AdminRepository adminRepository;
    private final ZoneRepository zoneRepository;

    public AdminServiceImpl(AdminRepository adminRepository, ZoneRepository zoneRepository) {
        this.adminRepository = adminRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    @Transactional
    public void addAdminToZone(AdminZoneAssignmentEvent event) {
        Zone zone = zoneRepository.findById(event.getZoneId()).orElse(null);

        if (zone == null) {
            logger.warn("Zone not found for ID: {}", event.getZoneId());
            return;
        }

        Optional<Admin> existingAdminOpt = adminRepository.findByAdminId(event.getAdminId());

        if (existingAdminOpt.isPresent()) {
            logger.warn("Admin with ID {} is already assigned to a zone", event.getAdminId());
            return;
        }

        Admin admin = AdminMapper.toEntity(event);
        admin.setZone(zone);
        zone.getAdmins().add(admin);

        zoneRepository.save(zone);

        logger.info("Admin {} successfully added to Zone '{}'", admin.getEmail(), zone.getName());
    }

    @Override
    @Transactional
    public void removeAdminFromZone(AdminZoneUnassignmentEvent event) {
        Zone zone = zoneRepository.findById(event.getZoneId()).orElse(null);

        if (zone == null) {
            logger.warn("Zone not found for ID: {}", event.getZoneId());
            return;
        }

        Optional<Admin> adminOpt = adminRepository.findByAdminId(event.getAdminId());

        if (adminOpt.isEmpty()) {
            logger.warn("Admin not found with ID: {}", event.getAdminId());
            return;
        }

        Admin admin = adminOpt.get();

        if (!zone.getAdmins().contains(admin)) {
            logger.warn("Admin with ID {} is not assigned to Zone '{}'", event.getAdminId(), zone.getName());
            return;
        }

        zone.getAdmins().remove(admin);
        zoneRepository.save(zone);

        logger.info("Admin {} removed from Zone '{}'", admin.getEmail(), zone.getName());
    }

}
