package com.management.nationalblood.meeting.service;

import com.management.nationalblood.meeting.dto.CreateAssessmentDTO;
import com.management.nationalblood.meeting.dto.AssessmentResponseDTO;
import com.management.nationalblood.meeting.enums.FormProgress;
import com.management.nationalblood.meeting.exception.BadRequestException;
import com.management.nationalblood.meeting.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AssessmentService {
    UUID initializeQuestionnaire(CreateAssessmentDTO questionnaireDTO) throws BadRequestException, NotFoundException;
    AssessmentResponseDTO getQuestionnaire(UUID donorId) throws NotFoundException, BadRequestException;

    Page<AssessmentResponseDTO> getQuestionnairesByCenterIds(
            List<UUID> centerIds, Pageable pageable,
            FormProgress formProgress, String donorName,
            LocalDateTime startDate, LocalDateTime endDate,
            LocalDateTime meetingStartDate, LocalDateTime meetingEndDate
    ) throws NotFoundException;

    Page<AssessmentResponseDTO> getQuestionnairesByCenter(
            UUID centerId, Pageable pageable,
            FormProgress formProgress, String donorName,
            LocalDateTime startDate, LocalDateTime endDate,
            LocalDateTime meetingStartDate, LocalDateTime meetingEndDate
    ) throws NotFoundException;

    Page<AssessmentResponseDTO> getQuestionnairesByMeeting(
            UUID meetingId, Pageable pageable,
            FormProgress formProgress, String donorName,
            LocalDateTime startDate, LocalDateTime endDate,
            LocalDateTime meetingStartDate, LocalDateTime meetingEndDate
    )
            throws NotFoundException, BadRequestException;

    AssessmentResponseDTO getDonorLastQuestionnaire(UUID donorId) throws NotFoundException, BadRequestException;
    Page<AssessmentResponseDTO> getDonorQuestionnaires(UUID donorId, Pageable pageable);
    AssessmentResponseDTO getQuestionnaireById(UUID questionnaireId) throws NotFoundException;

    void sendQuestionnaireCompletedEvent() throws NotFoundException, BadRequestException;

    Page<AssessmentResponseDTO> getQuestionnairesByCenterIdsAndFormStartedAtRange(
            List<UUID> centerIds,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );

}
