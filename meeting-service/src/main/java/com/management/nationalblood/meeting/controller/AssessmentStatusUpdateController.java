package com.management.nationalblood.meeting.controller;

import com.management.nationalblood.meeting.dto.*;
import com.management.nationalblood.meeting.exception.BadRequestException;
import com.management.nationalblood.meeting.exception.NotFoundException;
import com.management.nationalblood.meeting.exception.UnauthorizedAccessException;
import com.management.nationalblood.meeting.service.AssessmentStatusUpdateService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("assessment/progress")
public class AssessmentStatusUpdateController {
    private final AssessmentStatusUpdateService assessmentStatusUpdateService;

    public AssessmentStatusUpdateController(AssessmentStatusUpdateService assessmentStatusUpdateService) {
        this.assessmentStatusUpdateService = assessmentStatusUpdateService;
    }

    private UUID getStaffIdFromRequest(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (userId == null) {
            throw new UnauthorizedAccessException("User ID not found in request attributes");
        }
        return UUID.fromString(userId);
    }

    @PostMapping("preliminary/{meetingId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<AssessmentResponseDTO>> completePreliminary(
            @RequestBody PreliminaryAssessmentDTO dto,
            @PathVariable UUID meetingId,
            HttpServletRequest request) throws NotFoundException, BadRequestException {

        UUID staffId = getStaffIdFromRequest(request);

        MeetingResponseDTO<AssessmentResponseDTO> response = MeetingResponseDTO.ok(
                assessmentStatusUpdateService.completePreliminary(dto, meetingId, staffId),
                "Preliminary assessment completed",
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("physical-exam/{meetingId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<AssessmentResponseDTO>> completePhysicalExam(
            @RequestBody PhysicalExaminationDTO dto,
            @PathVariable UUID meetingId,
            HttpServletRequest request) throws NotFoundException, BadRequestException {

        UUID staffId = getStaffIdFromRequest(request);
        AssessmentResponseDTO response = assessmentStatusUpdateService.completePhysicalExam(dto, meetingId, staffId);
        return ResponseEntity.ok(
                MeetingResponseDTO.ok(response, "Physical exam completed", request.getRequestURI())
        );
    }

    @PostMapping("haematological-tests/{meetingId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<AssessmentResponseDTO>> completeHaematologicalTests(
            @RequestBody HaematologicalTestDTO dto,
            @PathVariable UUID meetingId,
            HttpServletRequest request) throws NotFoundException, BadRequestException {

        UUID staffId = getStaffIdFromRequest(request);
        AssessmentResponseDTO response = assessmentStatusUpdateService.completeHaematologicalTests(dto, meetingId, staffId);
        return ResponseEntity.ok(
                MeetingResponseDTO.ok(response, "Haematological tests completed", request.getRequestURI())
        );
    }

    @PostMapping("blood-pressure-pulse/{meetingId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<AssessmentResponseDTO>> completeBloodPressurePulse(
            @RequestBody BloodPressureAndPulseDTO dto,
            @PathVariable UUID meetingId,
            HttpServletRequest request) throws NotFoundException, BadRequestException {

        UUID staffId = getStaffIdFromRequest(request);
        AssessmentResponseDTO response = assessmentStatusUpdateService.completeBloodPressurePulse(dto, meetingId, staffId);
        return ResponseEntity.ok(
                MeetingResponseDTO.ok(response, "Blood pressure and pulse completed", request.getRequestURI())
        );
    }

    @PostMapping("final-evaluation/{meetingId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<AssessmentResponseDTO>> completeFinalEvaluation(
            @RequestBody FinalDonorEvaluationDTO dto,
            @PathVariable UUID meetingId,
            HttpServletRequest request) throws NotFoundException, BadRequestException {

        UUID staffId = getStaffIdFromRequest(request);
        AssessmentResponseDTO response = assessmentStatusUpdateService.completeFinalEvaluation(dto, meetingId, staffId);
        return ResponseEntity.ok(
                MeetingResponseDTO.ok(response, "Final evaluation completed", request.getRequestURI())
        );
    }

    @PostMapping("blood-collected/{meetingId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<AssessmentResponseDTO>> bloodCollected(
            @RequestBody BloodCollectionDataDTO dto,
            @PathVariable UUID meetingId,
            HttpServletRequest request) throws NotFoundException, BadRequestException {

        UUID staffId = getStaffIdFromRequest(request);
        AssessmentResponseDTO response = assessmentStatusUpdateService.bloodCollected(dto, meetingId, staffId);
        return ResponseEntity.ok(
                MeetingResponseDTO.ok(response, "Blood collection completed", request.getRequestURI())
        );
    }

    @PostMapping("adverse-event/{meetingId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<AssessmentResponseDTO>> adverseEvent(
            @RequestBody AdverseEventDTO dto,
            @PathVariable UUID meetingId,
            HttpServletRequest request) throws NotFoundException, BadRequestException {

        UUID staffId = getStaffIdFromRequest(request);
        AssessmentResponseDTO response = assessmentStatusUpdateService.adverseEvent(dto, meetingId, staffId);
        return ResponseEntity.ok(
                MeetingResponseDTO.ok(response, "Adverse event recorded", request.getRequestURI())
        );
    }
}
