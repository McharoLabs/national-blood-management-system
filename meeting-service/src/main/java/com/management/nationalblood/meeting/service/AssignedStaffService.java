package com.management.nationalblood.meeting.service;

import com.management.nationalblood.meeting.dto.AssignedStaffResponseDTO;

import java.util.List;
import java.util.UUID;

public interface AssignedStaffService {
    List<AssignedStaffResponseDTO> getActiveStaffByCenterId(UUID centerId);
}
