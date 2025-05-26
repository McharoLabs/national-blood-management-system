package com.nbtsms.identity_service.service.impl;

import com.nbtsms.identity_service.client.ZoneServiceClient;
import com.nbtsms.identity_service.constant.KafkaTopics;
import com.nbtsms.identity_service.entity.User;
import com.nbtsms.identity_service.event.AdminZoneAssignmentEvent;
import com.nbtsms.identity_service.event.AdminZoneUnassignmentEvent;
import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.ConflictException;
import com.nbtsms.identity_service.exception.NotFoundException;
import com.nbtsms.identity_service.repository.UserRepository;
import com.nbtsms.identity_service.service.ZoneAdminService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class ZoneAdminServiceImpl implements ZoneAdminService {
    private final UserRepository userRepository;
    private final ZoneServiceClient zoneServiceClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ZoneAdminServiceImpl(UserRepository userRepository, ZoneServiceClient zoneServiceClient, KafkaTemplate<String, Object> kafkaTemplate) {
        this.userRepository = userRepository;
        this.zoneServiceClient = zoneServiceClient;
        this.kafkaTemplate = kafkaTemplate;
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
/*
        boolean zoneExists = zoneServiceClient.zoneExists(zoneId);
        if (!zoneExists) {
            throw new NotFoundException(Map.of("zoneId", "Zone not found"));
        }

 */

        user.setZoneId(zoneId);

        kafkaTemplate.send(KafkaTopics.ADMIN_ZONE_ASSIGNMENT, new AdminZoneAssignmentEvent(
                user.getZoneId(),
                user.getId(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail())
        );

        userRepository.save(user);
    }


    @Override
    public void removeAdminFromZone(UUID zoneId, UUID adminId) throws NotFoundException {
        /*
        boolean zoneExists = zoneServiceClient.zoneExists(zoneId);
        if (!zoneExists) {
            throw new NotFoundException(Map.of("zoneId", "Zone not found"));
        }

         */

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException(Map.of("adminId", "Admin not found")));


        if (!admin.isZoneAdmin()) {
            throw new BadRequestException(Map.of("adminId", "This user is not assigned as admin to this zone."));
        }

        admin.setZoneId(null);

        kafkaTemplate.send(KafkaTopics.ADMIN_ZONE_UNASSIGNMENT, new AdminZoneUnassignmentEvent(
                zoneId,
                admin.getId()
        ));

        userRepository.save(admin);
    }

}
