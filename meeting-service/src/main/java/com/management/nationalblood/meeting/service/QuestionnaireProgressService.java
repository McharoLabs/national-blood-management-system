package com.management.nationalblood.meeting.service;


import com.management.nationalblood.meeting.dto.AssessmentResponseDTO;
import com.management.nationalblood.meeting.enums.FormProgress;
import com.management.nationalblood.meeting.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface QuestionnaireProgressService {
    List<AssessmentResponseDTO> getNotStartedQuestionnaires(UUID meetingId, String donorName) throws NotFoundException;
    List<AssessmentResponseDTO> getPreliminaryCompletedQuestionnaires(UUID meetingId, String donorName) throws NotFoundException;
    List<AssessmentResponseDTO> getPhysicalExamCompletedQuestionnaires(UUID meetingId, String donorName) throws NotFoundException;
    List<AssessmentResponseDTO> getHaematologicalTestsCompletedQuestionnaires(UUID meetingId, String donorName) throws NotFoundException;
    List<AssessmentResponseDTO> getBloodPressurePulseCompletedQuestionnaires(UUID meetingId, String donorName) throws NotFoundException;
    List<AssessmentResponseDTO> getFinalEvaluationCompletedQuestionnaires(UUID meetingId, String donorName) throws NotFoundException;
    List<AssessmentResponseDTO> getBloodCollectedQuestionnaires(UUID meetingId, String donorName) throws NotFoundException;
    List<AssessmentResponseDTO> completedQuestionnaires(UUID meetingId, String donorName) throws NotFoundException;
    List<AssessmentResponseDTO> getAllByMeetingId(UUID meetingId, String name, FormProgress status);
}
