package com.management.nationalblood.meeting.service;


import com.management.nationalblood.meeting.dto.*;
import com.management.nationalblood.meeting.exception.BadRequestException;
import com.management.nationalblood.meeting.exception.NotFoundException;

import java.util.UUID;

public interface AssessmentStatusUpdateService {
    AssessmentResponseDTO completePreliminary(PreliminaryAssessmentDTO preliminaryQuestionnaireDTO, UUID meetingId, UUID staffId) throws NotFoundException, BadRequestException;

    AssessmentResponseDTO completePhysicalExam(PhysicalExaminationDTO physicalExaminationDTO, UUID meetingId, UUID staffId) throws NotFoundException, BadRequestException;

    AssessmentResponseDTO completeHaematologicalTests(HaematologicalTestDTO haematologicalTestDTO, UUID meetingId, UUID staffId) throws NotFoundException, BadRequestException;

    AssessmentResponseDTO completeBloodPressurePulse(BloodPressureAndPulseDTO bloodPressureAndPulseDTO, UUID meetingId, UUID staffId) throws NotFoundException, BadRequestException;

    AssessmentResponseDTO completeFinalEvaluation(FinalDonorEvaluationDTO evaluationDTO, UUID meetingId, UUID staffId) throws NotFoundException, BadRequestException;

    AssessmentResponseDTO adverseEvent(AdverseEventDTO adverseEventDTO, UUID meetingId, UUID staffId) throws NotFoundException, BadRequestException;

    AssessmentResponseDTO bloodCollected(BloodCollectionDataDTO bloodCollectionDataDTO, UUID meetingId, UUID staffId) throws NotFoundException, BadRequestException;
}
