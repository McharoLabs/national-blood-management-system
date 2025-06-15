package com.nbtsms.identity_service.service.impl;

import com.nbtsms.identity_service.constant.KafkaTopics;
import com.nbtsms.identity_service.dto.UserDTO;
import com.nbtsms.identity_service.entity.User;
import com.nbtsms.identity_service.enums.Role;
import com.nbtsms.identity_service.event.StaffCenterAssignmentEvent;
import com.nbtsms.identity_service.event.StaffCenterUnassignmentEvent;
import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.ConflictException;
import com.nbtsms.identity_service.exception.NotFoundException;
import com.nbtsms.identity_service.mapper.UserMapper;
import com.nbtsms.identity_service.repository.UserRepository;
import com.nbtsms.identity_service.service.CenterStaffService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class CenterStaffServiceImpl implements CenterStaffService {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CenterStaffServiceImpl(UserRepository userRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void addStaffToCenter(UUID centerId, UUID staffId) throws NotFoundException, ConflictException, BadRequestException {

        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new NotFoundException(Map.of("detail", "Staff not found.")));

        if (staff.hasCounselorLabTechOrOrganizerRole()) {
            throw new BadRequestException(Map.of("detail", "Staff must have one of role: " + Set.of(Role.COUNSELOR, Role.ORGANIZER, Role.LAB_TECHNICIAN)));
        }

        if (staff.isAssignedToCenter()) {
            throw new BadRequestException(Map.of("detail", "Staff is already assigned to a center."));
        }

        staff.setCenterId(centerId);
        userRepository.save(staff);

        kafkaTemplate.send(KafkaTopics.STAFF_CENTER_ASSIGNMENT, new StaffCenterAssignmentEvent(
                centerId,
                staffId,
                staff.getFirstName(),
                staff.getMiddleName(),
                staff.getLastName(),
                staff.getPhoneNumber(),
                staff.getEmail()
        ));
    }

    @Override
    public void removeStaffFromCenter(UUID centerId, UUID staffId) throws NotFoundException, BadRequestException {

        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new NotFoundException(Map.of("detail", "Staff not found.")));

        if (!centerId.equals(staff.getCenterId())) {
            throw new BadRequestException(Map.of("detail", "Staff is not assigned to this center."));
        }

        staff.setCenterId(null);
        userRepository.save(staff);

        kafkaTemplate.send(KafkaTopics.STAFF_CENTER_UNASSIGNMENT, new StaffCenterUnassignmentEvent(
                centerId,
                staffId
        ));
    }

    @Override
    public List<UserDTO> centerStaffs(UUID centerId) {
        return userRepository.findCenterStaffByCenterId(centerId)
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

}
