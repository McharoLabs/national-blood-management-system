package com.management.nationalblood.meeting.service.impl;

import com.management.nationalblood.meeting.dto.*;
import com.management.nationalblood.meeting.entity.*;
import com.management.nationalblood.meeting.enums.FormProgress;
import com.management.nationalblood.meeting.exception.BadRequestException;
import com.management.nationalblood.meeting.exception.NotFoundException;
import com.management.nationalblood.meeting.mapper.*;
import com.management.nationalblood.meeting.repository.QuestionnaireRepository;
import com.management.nationalblood.meeting.service.AssessmentStatusUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class AssessmentStatusUpdateServiceImpl implements AssessmentStatusUpdateService {
    private static final Logger logger = LoggerFactory.getLogger(AssessmentStatusUpdateServiceImpl.class);

    private final QuestionnaireRepository questionnaireRepository;

    public AssessmentStatusUpdateServiceImpl(QuestionnaireRepository questionnaireRepository) {
        this.questionnaireRepository = questionnaireRepository;
    }

    @Override
    public AssessmentResponseDTO completePreliminary(PreliminaryAssessmentDTO preliminaryQuestionnaireDTO, UUID meetingId, UUID staffId) throws NotFoundException, BadRequestException {
        logger.info("Starting preliminary assessment completion for meetingId={}, staffId={}", meetingId, staffId);

        Questionnaire questionnaire = getQuestionnaire(preliminaryQuestionnaireDTO.getQuestionnaireId(), FormProgress.PHYSICAL_EXAM_COMPLETED);

        PreliminaryQuestionnaire preliminaryQuestionnaire = PreliminaryAssessmentMapper.toEntity(preliminaryQuestionnaireDTO);
        preliminaryQuestionnaire.setAssessedBy(staffId);

        questionnaire.setPreliminaryQuestionnaire(preliminaryQuestionnaire);
        questionnaire.setFormProgress(FormProgress.PRELIMINARY_COMPLETED);
        questionnaireRepository.save(questionnaire);

        logger.info("Preliminary assessment completed for meetingId={}, staffId={}", meetingId, staffId);

        return AssessmentMapper.toResponse(questionnaire);
    }

    private Questionnaire getQuestionnaire(UUID id, FormProgress expectedCurrent) throws NotFoundException {
        logger.debug("Fetching questionnaire for meetingId={} with expected form progress {}", id, expectedCurrent);
        return questionnaireRepository.findByIdAndFormProgress(id, expectedCurrent)
                .orElseThrow(() -> {
                    logger.error("No questionnaire found for meetingId={} with form progress {}", id, expectedCurrent);
                    return new NotFoundException(Map.of("detail", "No assessment found"));
                });
    }

    @Override
    public AssessmentResponseDTO completePhysicalExam(PhysicalExaminationDTO physicalExaminationDTO, UUID meetingId, UUID staffId) throws NotFoundException, BadRequestException {
        logger.info("Completing physical exam for meetingId={}, staffId={}", meetingId, staffId);

        Questionnaire questionnaire = getQuestionnaire(physicalExaminationDTO.getQuestionnaireId(), FormProgress.NOT_STARTED);

        PhysicalExamination physicalExamination = PhysicalExaminationMapper.toEntity(physicalExaminationDTO);
        physicalExamination.setMeasuredBy(staffId);

        questionnaire.setPhysicalExamination(physicalExamination);
        questionnaire.setFormProgress(FormProgress.PHYSICAL_EXAM_COMPLETED);
        questionnaireRepository.save(questionnaire);

        logger.info("Physical exam completed for meetingId={}, staffId={}", meetingId, staffId);
        return AssessmentMapper.toResponse(questionnaire);
    }

    @Override
    public AssessmentResponseDTO completeHaematologicalTests(HaematologicalTestDTO haematologicalTestDTO, UUID meetingId, UUID staffId) throws NotFoundException, BadRequestException {
        logger.info("Completing haematological tests for meetingId={}, staffId={}", meetingId, staffId);

        Questionnaire questionnaire = getQuestionnaire(haematologicalTestDTO.getQuestionnaireId(), FormProgress.PRELIMINARY_COMPLETED);

        HaematologicalTest haematologicalTest = HaematologicalTestMapper.toEntity(haematologicalTestDTO);
        haematologicalTest.setMeasuredBy(staffId);

        questionnaire.setHaematologicalTest(haematologicalTest);
        questionnaire.setFormProgress(FormProgress.HAEMATOLOGICAL_TESTS_COMPLETED);
        questionnaireRepository.save(questionnaire);

        logger.info("Haematological tests completed for meetingId={}, staffId={}", meetingId, staffId);
        return AssessmentMapper.toResponse(questionnaire);
    }

    @Override
    public AssessmentResponseDTO completeBloodPressurePulse(BloodPressureAndPulseDTO bloodPressureAndPulseDTO, UUID meetingId, UUID staffId) throws NotFoundException, BadRequestException {
        logger.info("Completing blood pressure and pulse for meetingId={}, staffId={}", meetingId, staffId);

        Questionnaire questionnaire = getQuestionnaire(bloodPressureAndPulseDTO.getQuestionnaireId(), FormProgress.HAEMATOLOGICAL_TESTS_COMPLETED);

        BloodPressureAndPulse bloodPressureAndPulse = BloodPressureAndPulseMapper.toEntity(bloodPressureAndPulseDTO);
        bloodPressureAndPulse.setMeasuredBy(staffId);

        questionnaire.setBloodPressureAndPulse(bloodPressureAndPulse);
        questionnaire.setFormProgress(FormProgress.BLOOD_PRESSURE_PULSE_COMPLETED);
        questionnaireRepository.save(questionnaire);

        logger.info("Blood pressure and pulse completed for meetingId={}, staffId={}", meetingId, staffId);
        return AssessmentMapper.toResponse(questionnaire);
    }

    @Override
    public AssessmentResponseDTO completeFinalEvaluation(FinalDonorEvaluationDTO evaluationDTO, UUID meetingId, UUID staffId) throws NotFoundException, BadRequestException {
        logger.info("Completing final evaluation for meetingId={}, staffId={}", meetingId, staffId);

        Questionnaire questionnaire = getQuestionnaire(evaluationDTO.getQuestionnaireId(), FormProgress.BLOOD_PRESSURE_PULSE_COMPLETED);

        FinalDonorEvaluation evaluation = FinalDonorEvaluationMapper.toEntity(evaluationDTO);
        evaluation.setCounselor(staffId);

        questionnaire.setFinalDonorEvaluation(evaluation);
        questionnaire.setFormProgress(FormProgress.FINAL_EVALUATION_COMPLETED);
        questionnaireRepository.save(questionnaire);

        logger.info("Final evaluation completed for meetingId={}, staffId={}", meetingId, staffId);
        return AssessmentMapper.toResponse(questionnaire);
    }

    @Override
    public AssessmentResponseDTO bloodCollected(BloodCollectionDataDTO bloodCollectionDataDTO, UUID meetingId, UUID staffId) throws NotFoundException, BadRequestException {
        logger.info("Marking blood collected for meetingId={}, staffId={}", meetingId, staffId);

        // Fixed: Should use meetingId here, not staffId
        Questionnaire questionnaire = getQuestionnaire(bloodCollectionDataDTO.getQuestionnaireId(), FormProgress.FINAL_EVALUATION_COMPLETED);

        BloodCollectionData collectionData = BloodCollectionDataMapper.toEntity(bloodCollectionDataDTO);
        collectionData.setFinalizedBy(staffId);

        questionnaire.setBloodCollectionData(collectionData);
        questionnaire.setFormProgress(FormProgress.BLOOD_COLLECTED);
        questionnaireRepository.save(questionnaire);

        logger.info("Blood collection marked for meetingId={}, staffId={}", meetingId, staffId);
        return AssessmentMapper.toResponse(questionnaire);
    }

    @Override
    public AssessmentResponseDTO adverseEvent(AdverseEventDTO adverseEventDTO, UUID meetingId, UUID staffId) throws NotFoundException, BadRequestException {
        logger.info("Recording adverse event for meetingId={}, staffId={}", meetingId, staffId);

        Questionnaire questionnaire = getQuestionnaire(adverseEventDTO.getQuestionnaireId(), FormProgress.BLOOD_COLLECTED);

        AdverseEvent event = AdverseEventMapper.toEntity(adverseEventDTO);
        questionnaire.setAdverseEvent(event);
        questionnaire.setFormProgress(FormProgress.COMPLETED);
        questionnaireRepository.save(questionnaire);

        logger.info("Adverse event recorded for meetingId={}, staffId={}", meetingId, staffId);
        return AssessmentMapper.toResponse(questionnaire);
    }
}
