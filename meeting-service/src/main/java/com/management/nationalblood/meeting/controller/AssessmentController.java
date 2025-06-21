package com.management.nationalblood.meeting.controller;

import com.management.nationalblood.meeting.dto.AssessmentResponseDTO;
import com.management.nationalblood.meeting.dto.CreateAssessmentDTO;
import com.management.nationalblood.meeting.dto.MeetingResponseDTO;
import com.management.nationalblood.meeting.dto.QuestionnaireFilterRequest;
import com.management.nationalblood.meeting.enums.FormProgress;
import com.management.nationalblood.meeting.service.impl.AssessmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("assessments")
public class AssessmentController {
    private final AssessmentServiceImpl assessmentService;

    public AssessmentController(AssessmentServiceImpl assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping("questionnaires/filter")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<Page<AssessmentResponseDTO>>> getQuestionnairesByCenterIdsAndDateRange(
            @RequestParam List<UUID> centerIds,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "formStartedAt") String sortBy,
            HttpServletRequest request
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<AssessmentResponseDTO> assessments = assessmentService.getQuestionnairesByCenterIdsAndFormStartedAtRange(centerIds, startDate, endDate, pageable);

        MeetingResponseDTO<Page<AssessmentResponseDTO>> response = MeetingResponseDTO.ok(
                assessments,
                "Filtered questionnaires retrieved successfully",
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<Map<String, Object>>> initializeQuestionnaire(@RequestBody CreateAssessmentDTO dto, HttpServletRequest request) {
        UUID id = assessmentService.initializeQuestionnaire(dto);
        return new ResponseEntity<>(MeetingResponseDTO.ok(Map.of("assessmentId", id), "Assessment initialized", request.getRequestURI()), HttpStatus.CREATED);
    }

    @GetMapping("donor/{donorId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<AssessmentResponseDTO>> getQuestionnaire(@PathVariable UUID donorId, HttpServletRequest request) {
        return ResponseEntity.ok(MeetingResponseDTO.ok(assessmentService.getQuestionnaire(donorId), "Assessment retrieved", request.getRequestURI()));
    }

    @GetMapping("donor/{donorId}/last")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<AssessmentResponseDTO>> getDonorLastQuestionnaire(@PathVariable UUID donorId, HttpServletRequest request) {
        return ResponseEntity.ok(MeetingResponseDTO.ok(assessmentService.getDonorLastQuestionnaire(donorId), "Last assessment retrieved", request.getRequestURI()));
    }

    @GetMapping("donor/{donorId}/all")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<Page<AssessmentResponseDTO>>> getDonorQuestionnaires(
            @PathVariable UUID donorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "formStartedAt") String sortBy,
            HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(MeetingResponseDTO.ok(assessmentService.getDonorQuestionnaires(donorId, pageable), "Donor assessments retrieved", request.getRequestURI()));
    }

    @GetMapping("{questionnaireId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<AssessmentResponseDTO>> getQuestionnaireById(@PathVariable UUID questionnaireId, HttpServletRequest request) {
        return ResponseEntity.ok(MeetingResponseDTO.ok(assessmentService.getQuestionnaireById(questionnaireId), "Assessment retrieved", request.getRequestURI()));
    }

    @GetMapping("by-centers")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<Page<AssessmentResponseDTO>>> getQuestionnairesByCenterIds(
            @Valid @RequestBody QuestionnaireFilterRequest questionnaireFilterRequest,
            @RequestParam(required = false) FormProgress formProgress,
            @RequestParam(required = false) String donorName,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) LocalDateTime meetingStartDate,
            @RequestParam(required = false) LocalDateTime meetingEndDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "formStartedAt") String sortBy,
            HttpServletRequest request
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(
                MeetingResponseDTO.ok(
                        assessmentService.getQuestionnairesByCenterIds(questionnaireFilterRequest.getCenterIds(), pageable, formProgress, donorName, startDate, endDate, meetingStartDate, meetingEndDate),
                        "Zone assessments retrieved",
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("center/{centerId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<Page<AssessmentResponseDTO>>> getQuestionnairesByCenter(
            @PathVariable UUID centerId,
            @RequestParam(required = false) FormProgress formProgress,
            @RequestParam(required = false) String donorName,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) LocalDateTime meetingStartDate,
            @RequestParam(required = false) LocalDateTime meetingEndDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "formStartedAt") String sortBy,
            HttpServletRequest request
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(
                MeetingResponseDTO.ok(
                        assessmentService.getQuestionnairesByCenter(centerId, pageable, formProgress, donorName, startDate, endDate, meetingStartDate, meetingEndDate),
                        "Center assessments retrieved",
                        request.getRequestURI()
                )
        );
    }

    @GetMapping("meeting/{meetingId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<Page<AssessmentResponseDTO>>> getQuestionnairesByMeeting(
            @PathVariable UUID meetingId,
            @RequestParam(required = false) FormProgress formProgress,
            @RequestParam(required = false) String donorName,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) LocalDateTime meetingStartDate,
            @RequestParam(required = false) LocalDateTime meetingEndDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "formStartedAt") String sortBy,
            HttpServletRequest request
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(
                MeetingResponseDTO.ok(
                        assessmentService.getQuestionnairesByMeeting(meetingId, pageable, formProgress, donorName, startDate, endDate, meetingStartDate, meetingEndDate),
                        "Meeting assessments retrieved",
                        request.getRequestURI()
                )
        );
    }
}
