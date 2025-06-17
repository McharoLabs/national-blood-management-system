package com.management.nationalblood.meeting.controller;

import com.management.nationalblood.meeting.dto.AssessmentResponseDTO;
import com.management.nationalblood.meeting.dto.MeetingResponseDTO;
import com.management.nationalblood.meeting.enums.FormProgress;
import com.management.nationalblood.meeting.service.QuestionnaireProgressService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("assessment-progress")
public class QuestionnaireProgressController {
    private final QuestionnaireProgressService questionnaireProgressService;

    public QuestionnaireProgressController(QuestionnaireProgressService questionnaireProgressService) {
        this.questionnaireProgressService = questionnaireProgressService;
    }

    @GetMapping("not-started")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<List<AssessmentResponseDTO>>> getNotStarted(
            @RequestParam UUID meetingId,
            @RequestParam(required = false, defaultValue = "") String donorName,
            HttpServletRequest request) {

        List<AssessmentResponseDTO> responses = questionnaireProgressService.getNotStartedQuestionnaires(meetingId, donorName);
        return ResponseEntity.ok(MeetingResponseDTO.ok(responses, "Not started questionnaires retrieved", request.getRequestURI()));
    }

    @GetMapping("preliminary-completed")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<List<AssessmentResponseDTO>>> getPreliminaryCompleted(
            @RequestParam UUID meetingId,
            @RequestParam(required = false, defaultValue = "") String donorName,
            HttpServletRequest request) {

        List<AssessmentResponseDTO> responses = questionnaireProgressService.getPreliminaryCompletedQuestionnaires(meetingId, donorName);
        return ResponseEntity.ok(MeetingResponseDTO.ok(responses, "Preliminary completed questionnaires retrieved", request.getRequestURI()));
    }

    @GetMapping("physical-exam-completed")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<List<AssessmentResponseDTO>>> getPhysicalExamCompleted(
            @RequestParam UUID meetingId,
            @RequestParam(required = false, defaultValue = "") String donorName,
            HttpServletRequest request) {

        List<AssessmentResponseDTO> responses = questionnaireProgressService.getPhysicalExamCompletedQuestionnaires(meetingId, donorName);
        return ResponseEntity.ok(MeetingResponseDTO.ok(responses, "Physical exam completed questionnaires retrieved", request.getRequestURI()));
    }

    @GetMapping("haematological-tests-completed")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<List<AssessmentResponseDTO>>> getHaematologicalTestsCompleted(
            @RequestParam UUID meetingId,
            @RequestParam(required = false, defaultValue = "") String donorName,
            HttpServletRequest request) {

        List<AssessmentResponseDTO> responses = questionnaireProgressService.getHaematologicalTestsCompletedQuestionnaires(meetingId, donorName);
        return ResponseEntity.ok(MeetingResponseDTO.ok(responses, "Haematological tests completed questionnaires retrieved", request.getRequestURI()));
    }

    @GetMapping("blood-pressure-pulse-completed")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<List<AssessmentResponseDTO>>> getBloodPressurePulseCompleted(
            @RequestParam UUID meetingId,
            @RequestParam(required = false, defaultValue = "") String donorName,
            HttpServletRequest request) {

        List<AssessmentResponseDTO> responses = questionnaireProgressService.getBloodPressurePulseCompletedQuestionnaires(meetingId, donorName);
        return ResponseEntity.ok(MeetingResponseDTO.ok(responses, "Blood pressure/pulse completed questionnaires retrieved", request.getRequestURI()));
    }

    @GetMapping("final-evaluation-completed")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<List<AssessmentResponseDTO>>> getFinalEvaluationCompleted(
            @RequestParam UUID meetingId,
            @RequestParam(required = false, defaultValue = "") String donorName,
            HttpServletRequest request) {

        List<AssessmentResponseDTO> responses = questionnaireProgressService.getFinalEvaluationCompletedQuestionnaires(meetingId, donorName);
        return ResponseEntity.ok(MeetingResponseDTO.ok(responses, "Final evaluation completed questionnaires retrieved", request.getRequestURI()));
    }

    @GetMapping("blood-collected")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<List<AssessmentResponseDTO>>> getBloodCollected(
            @RequestParam UUID meetingId,
            @RequestParam(required = false, defaultValue = "") String donorName,
            HttpServletRequest request) {

        List<AssessmentResponseDTO> responses = questionnaireProgressService.getBloodCollectedQuestionnaires(meetingId, donorName);
        return ResponseEntity.ok(MeetingResponseDTO.ok(responses, "Blood collected questionnaires retrieved", request.getRequestURI()));
    }

    @GetMapping("completed")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<List<AssessmentResponseDTO>>> getCompleted(
            @RequestParam UUID meetingId,
            @RequestParam(required = false, defaultValue = "") String donorName,
            HttpServletRequest request) {

        List<AssessmentResponseDTO> responses = questionnaireProgressService.completedQuestionnaires(meetingId, donorName);
        return ResponseEntity.ok(MeetingResponseDTO.ok(responses, "Completed questionnaires retrieved", request.getRequestURI()));
    }

    @GetMapping("by-status")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    public ResponseEntity<MeetingResponseDTO<List<AssessmentResponseDTO>>> getBySingleStatus(
            @RequestParam UUID meetingId,
            @RequestParam(required = false, defaultValue = "") String donorName,
            @RequestParam FormProgress status,
            HttpServletRequest request) {

        List<AssessmentResponseDTO> responses = questionnaireProgressService.getAllByMeetingId(meetingId, donorName, status);
        return ResponseEntity.ok(MeetingResponseDTO.ok(responses, "Questionnaires by single status retrieved", request.getRequestURI()));
    }
}
