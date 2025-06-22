package com.management.nationalblood.meeting.controller;

import com.management.nationalblood.meeting.dto.AssignedStaffResponseDTO;
import com.management.nationalblood.meeting.dto.MeetingResponseDTO;
import com.management.nationalblood.meeting.service.impl.AssignedStaffServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("staff")
public class StaffAssignmentController {
    private final AssignedStaffServiceImpl staffService;

    public StaffAssignmentController(AssignedStaffServiceImpl staffService) {
        this.staffService = staffService;
    }

    @GetMapping("center/{centerId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<List<AssignedStaffResponseDTO>>> getActiveMeetingsWithStaffByCenter(
            @PathVariable UUID centerId,
            HttpServletRequest request
    ) {
        MeetingResponseDTO<List<AssignedStaffResponseDTO>> response = MeetingResponseDTO.ok(
                staffService.getActiveStaffByCenterId(centerId),
                "Staffs retrieved successfully",
                request.getRequestURI()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
