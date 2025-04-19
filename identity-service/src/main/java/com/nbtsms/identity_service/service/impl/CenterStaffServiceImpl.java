package com.nbtsms.identity_service.service.impl;

import com.management.nationalblood.shared.dto.CenterResponseDTO;
import com.nbtsms.identity_service.client.ZoneServiceClient;
import com.nbtsms.identity_service.entity.User;
import com.nbtsms.identity_service.enums.Role;
import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.ConflictException;
import com.nbtsms.identity_service.exception.NotFoundException;
import com.nbtsms.identity_service.repository.UserRepository;
import com.nbtsms.identity_service.service.CenterStaffService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class CenterStaffServiceImpl implements CenterStaffService {
    private final UserRepository userRepository;
    private final ZoneServiceClient zoneServiceClient;

    public CenterStaffServiceImpl(UserRepository userRepository, ZoneServiceClient zoneServiceClient) {
        this.userRepository = userRepository;
        this.zoneServiceClient = zoneServiceClient;
    }

    @Override
    public void addStaffToCenter(UUID centerId, UUID staffId, UUID adminId) throws NotFoundException, ConflictException, BadRequestException {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException(Map.of("adminId", "Could not find your admin details.")));

        if (!admin.isZoneAdmin()) {
            throw new BadRequestException(Map.of("adminId", "You are not assigned as an admin to any zone."));
        }


        boolean centerExists = zoneServiceClient.centerExists(centerId);
        boolean zoneExists = zoneServiceClient.zoneExists(admin.getZoneId());


        if (!centerExists) {
            throw new NotFoundException(Map.of("detail", "Center not found"));
        }


        if (!zoneExists) {
            throw new NotFoundException(Map.of("detail", "Zone not found"));
        }

        CenterResponseDTO centerResponseDTO = zoneServiceClient.fetchCenter(centerId);

        if (!admin.getZoneId().equals(centerResponseDTO.getRegion().getZone().getId())) {
            throw new BadRequestException(Map.of("centerId", "This center does not belong to your zone."));
        }

        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new NotFoundException(Map.of("staffId", "Staff not found.")));

        if (staff.hasCounselorLabTechOrOrganizerRole()) {
            throw new BadRequestException(Map.of("staffId", "Staff must have one of role: " + Set.of(Role.COUNSELOR, Role.ORGANIZER, Role.LAB_TECHNICIAN)));
        }

        if (staff.isAssignedToCenter()) {
            throw new BadRequestException(Map.of("staffId", "Staff is already assigned to a center."));
        }

        staff.setCenterId(centerResponseDTO.getId());
        userRepository.save(staff);
    }

    @Override
    public void removeStaffFromCenter(UUID centerId, UUID staffId, UUID adminId) throws NotFoundException, BadRequestException {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException(Map.of("adminId", "Could not find your admin details.")));

        if (!admin.isZoneAdmin()) {
            throw new BadRequestException(Map.of("adminId", "You are not assigned as an admin to any zone."));
        }

        boolean centerExists = zoneServiceClient.centerExists(centerId);
        if (!centerExists) {
            throw new NotFoundException(Map.of("centerId", "Center not found"));
        }

        CenterResponseDTO centerResponseDTO = zoneServiceClient.fetchCenter(centerId);

        if (!admin.getZoneId().equals(centerResponseDTO.getRegion().getZone().getId())) {
            throw new BadRequestException(Map.of("centerId", "This center does not belong to your zone."));
        }

        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new NotFoundException(Map.of("staffId", "Staff not found.")));

        if (!centerId.equals(staff.getCenterId())) {
            throw new BadRequestException(Map.of("centerId", "Staff is not assigned to this center."));
        }

        staff.setCenterId(null);
        userRepository.save(staff);
    }

}
