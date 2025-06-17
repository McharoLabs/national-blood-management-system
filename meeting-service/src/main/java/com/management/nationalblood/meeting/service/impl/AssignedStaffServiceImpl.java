package com.management.nationalblood.meeting.service.impl;

import com.management.nationalblood.meeting.dto.AssignedStaffResponseDTO;
import com.management.nationalblood.meeting.enums.MeetingStatus;
import com.management.nationalblood.meeting.mapper.StaffAssignmentMapper;
import com.management.nationalblood.meeting.repository.AssignedStaffRepository;
import com.management.nationalblood.meeting.service.AssignedStaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional(isolation = Isolation.SERIALIZABLE)
@Service
public class AssignedStaffServiceImpl implements AssignedStaffService {
    private static final Logger logger = LoggerFactory.getLogger(AssignedStaffServiceImpl.class);
    private final AssignedStaffRepository staffRepository;

    public AssignedStaffServiceImpl(AssignedStaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public List<AssignedStaffResponseDTO> getActiveStaffByCenterId(UUID centerId) {
        return staffRepository.findActiveStaffByCenterId(centerId, List.of(MeetingStatus.CANCELLED, MeetingStatus.COMPLETED))
                .stream()
                .map(StaffAssignmentMapper::toResponse)
                .collect(Collectors.toList());
    }
}
