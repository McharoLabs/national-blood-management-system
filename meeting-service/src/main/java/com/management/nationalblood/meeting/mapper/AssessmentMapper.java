package com.management.nationalblood.meeting.mapper;

import com.management.nationalblood.meeting.dto.AssessmentResponseDTO;
import com.management.nationalblood.meeting.entity.Questionnaire;

public class AssessmentMapper {

    public static AssessmentResponseDTO toResponse(Questionnaire questionnaire) {
        return AssessmentResponseDTO.builder()
                .id(questionnaire.getId())
                .donor(
                        questionnaire.getDonor() != null
                                ? DonorMapper.toResponseDTO(questionnaire.getDonor())
                                : null
                )
                .adverseEvent(
                        questionnaire.getAdverseEvent() != null
                                ? AdverseEventMapper.toResponse(questionnaire.getAdverseEvent())
                                : null
                )
                .bloodCollectionData(
                        questionnaire.getBloodCollectionData() != null
                                ? BloodCollectionDataMapper.toResponse(questionnaire.getBloodCollectionData())
                                : null
                )
                .finalDonorEvaluation(
                        questionnaire.getFinalDonorEvaluation() != null
                                ? FinalDonorEvaluationMapper.toResponse(questionnaire.getFinalDonorEvaluation())
                                : null
                )
                .meetingTitle(
                        questionnaire.getMeeting() != null
                                ? questionnaire.getMeeting().getTitle()
                                : null
                )
                .meetingId(
                        questionnaire.getMeeting() != null
                                ? questionnaire.getMeeting().getId()
                                : null
                )
                .formProgress(questionnaire.getFormProgress())
                .formStartedAt(questionnaire.getFormStartedAt())
                .lastUpdatedAt(questionnaire.getLastUpdatedAt())
                .haematologicalTest(
                        questionnaire.getHaematologicalTest() != null
                                ? HaematologicalTestMapper.toResponse(questionnaire.getHaematologicalTest())
                                : null
                )
                .build();
    }
}
