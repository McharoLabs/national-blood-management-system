package com.management.nationalblood.meeting.service.impl;

import com.management.nationalblood.meeting.dto.AssessmentResponseDTO;
import com.management.nationalblood.meeting.entity.Questionnaire;
import com.management.nationalblood.meeting.enums.FormProgress;
import com.management.nationalblood.meeting.exception.NotFoundException;
import com.management.nationalblood.meeting.mapper.AssessmentMapper;
import com.management.nationalblood.meeting.repository.QuestionnaireRepository;
import com.management.nationalblood.meeting.service.QuestionnaireProgressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class QuestionnaireProgressServiceImpl implements QuestionnaireProgressService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionnaireProgressServiceImpl.class);
    private final QuestionnaireRepository questionnaireRepository;

    public QuestionnaireProgressServiceImpl(QuestionnaireRepository questionnaireRepository) {
        this.questionnaireRepository = questionnaireRepository;
    }


    @Override
    public List<AssessmentResponseDTO> getNotStartedQuestionnaires(UUID meetingId, String donorName) throws NotFoundException {
        return getQuestionnaireResponseDTOS(meetingId, donorName, List.of(FormProgress.NOT_STARTED));
    }

    private List<AssessmentResponseDTO> getQuestionnaireResponseDTOS(UUID meetingId, String donorName, List<FormProgress> formProgress) {

        List<Questionnaire> assessments = questionnaireRepository.findByMeetingIdAndFormProgressInAndOptionalDonorName(meetingId, donorName, formProgress);

        return assessments
                .stream()
                .map(AssessmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssessmentResponseDTO> getPreliminaryCompletedQuestionnaires(UUID staffId, String donorName) throws NotFoundException {
        return getQuestionnaireResponseDTOS(staffId, donorName, List.of(FormProgress.PRELIMINARY_COMPLETED));
    }

    @Override
    public List<AssessmentResponseDTO> getPhysicalExamCompletedQuestionnaires(UUID staffId, String donorName) throws NotFoundException {
        return getQuestionnaireResponseDTOS(staffId, donorName, List.of(FormProgress.PHYSICAL_EXAM_COMPLETED));
    }

    @Override
    public List<AssessmentResponseDTO> getHaematologicalTestsCompletedQuestionnaires(UUID staffId, String donorName) throws NotFoundException {
        return getQuestionnaireResponseDTOS(staffId, donorName, List.of(FormProgress.HAEMATOLOGICAL_TESTS_COMPLETED));
    }

    @Override
    public List<AssessmentResponseDTO> getBloodPressurePulseCompletedQuestionnaires(UUID staffId, String donorName) throws NotFoundException {
        return getQuestionnaireResponseDTOS(staffId, donorName, List.of(FormProgress.BLOOD_PRESSURE_PULSE_COMPLETED));
    }

    @Override
    public List<AssessmentResponseDTO> getFinalEvaluationCompletedQuestionnaires(UUID staffId, String donorName) throws NotFoundException {
        return getQuestionnaireResponseDTOS(staffId, donorName, List.of(FormProgress.FINAL_EVALUATION_COMPLETED));
    }

    @Override
    public List<AssessmentResponseDTO> getBloodCollectedQuestionnaires(UUID staffId, String donorName) throws NotFoundException {
        return getQuestionnaireResponseDTOS(staffId, donorName, List.of(FormProgress.BLOOD_COLLECTED));
    }

    @Override
    public List<AssessmentResponseDTO> completedQuestionnaires(UUID staffId, String donorName) throws NotFoundException {
        return getQuestionnaireResponseDTOS(staffId, donorName, List.of(FormProgress.COMPLETED));
    }

    @Override
    public List<AssessmentResponseDTO> getAllByMeetingId(UUID meetingId, String name, FormProgress status) {
        return questionnaireRepository.findAllByMeetingIdAndOptionalDonorNameAndStatus(meetingId, name, status)
                .stream()
                .map(AssessmentMapper::toResponse)
                .collect(Collectors.toList());
    }
}
