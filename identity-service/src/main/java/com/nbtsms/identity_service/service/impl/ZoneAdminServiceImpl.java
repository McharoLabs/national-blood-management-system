package com.nbtsms.identity_service.service.impl;

import com.nbtsms.identity_service.client.ZoneServiceClient;
import com.nbtsms.identity_service.entity.User;
import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.ConflictException;
import com.nbtsms.identity_service.exception.NotFoundException;
import com.nbtsms.identity_service.repository.UserRepository;
import com.nbtsms.identity_service.service.ZoneAdminService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class ZoneAdminServiceImpl implements ZoneAdminService {
    private final UserRepository userRepository;
    private final ZoneServiceClient zoneServiceClient;

    public ZoneAdminServiceImpl(UserRepository userRepository, ZoneServiceClient zoneServiceClient) {
        this.userRepository = userRepository;
        this.zoneServiceClient = zoneServiceClient;
    }

    @Override
    public void assignAdminToZone(UUID zoneId, UUID adminId) throws NotFoundException, ConflictException, BadRequestException {
        User user = userRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException(Map.of("adminId", "User not found")));

        if (!user.isAdmin()) {
            throw new BadRequestException(Map.of("adminId", "User provided has no admin role"));
        }

        if (user.isZoneAdmin()) {
            throw new BadRequestException(Map.of("adminId", "User already assigned to a zone"));
        }

        boolean zoneExists = zoneServiceClient.zoneExists(zoneId);
        if (!zoneExists) {
            throw new NotFoundException(Map.of("zoneId", "Zone not found"));
        }

        user.setZoneId(zoneId);
        userRepository.save(user);
    }


    @Override
    public void removeAdminFromZone(UUID zoneId, UUID adminId) throws NotFoundException {
        boolean zoneExists = zoneServiceClient.zoneExists(zoneId);
        if (!zoneExists) {
            throw new NotFoundException(Map.of("zoneId", "Zone not found"));
        }

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException(Map.of("adminId", "Admin not found")));

        if (!admin.isZoneAdmin()) {
            throw new BadRequestException(Map.of("adminId", "This user is not assigned as admin to this zone."));
        }

        admin.setZoneId(null);
        userRepository.save(admin);
    }

}
