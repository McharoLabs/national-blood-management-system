package com.management.nationalblood.laboratoryservice.service.impl;

import com.management.nationalblood.laboratoryservice.dto.AssessmentResponseDTO;
import com.management.nationalblood.laboratoryservice.dto.CreateLabTestResultDTO;
import com.management.nationalblood.laboratoryservice.entity.Assessment;
import com.management.nationalblood.laboratoryservice.entity.LabTestResult;
import com.management.nationalblood.laboratoryservice.event.QuestionnaireCompletedEvent;
import com.management.nationalblood.laboratoryservice.exception.BadRequestException;
import com.management.nationalblood.laboratoryservice.exception.NotFoundException;
import com.management.nationalblood.laboratoryservice.mapper.AssessmentMapper;
import com.management.nationalblood.laboratoryservice.mapper.LabTestResultMapper;
import com.management.nationalblood.laboratoryservice.repository.AssessmentRepository;
import com.management.nationalblood.laboratoryservice.repository.LabTestResultRepository;
import com.management.nationalblood.laboratoryservice.service.AssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Transactional(isolation = Isolation.SERIALIZABLE)
@Service
public class AssessmentServiceImpl implements AssessmentService {
    private static final Logger logger = LoggerFactory.getLogger(AssessmentServiceImpl.class);
    private final AssessmentRepository assessmentRepository;
    private final LabTestResultRepository labTestResultRepository;

    public AssessmentServiceImpl(AssessmentRepository assessmentRepository, LabTestResultRepository labTestResultRepository) {
        this.assessmentRepository = assessmentRepository;
        this.labTestResultRepository = labTestResultRepository;
    }

    @Override
    public void handleCompletedAssessmentForTest(QuestionnaireCompletedEvent event) {
        logger.info("Received QuestionnaireCompletedEvent for questionnaireId: {}", event.getQuestionnaireId());

        try {
            boolean exists = assessmentRepository.existsByQuestionnaireId(event.getQuestionnaireId());
            if (exists) {
                logger.warn("Assessment with questionnaireId {} already exists. Skipping save.", event.getQuestionnaireId());
                return;
            }

            Assessment assessment = AssessmentMapper.toEntity(event);
            assessmentRepository.save(assessment);
            logger.info("Assessment saved for questionnaireId: {}", event.getQuestionnaireId());

        } catch (Exception e) {
            logger.error("Failed to save assessment for questionnaireId {}: {}", event.getQuestionnaireId(), e.getMessage(), e);
        }
    }

    @Override
    public Page<AssessmentResponseDTO> getAssessmentForTest(UUID centerId, String bloodBagLotNumber, Pageable pageable) {
        return assessmentRepository.findPendingAssessmentsByCenterAndOptionalBloodBagLotNumber(centerId, bloodBagLotNumber, pageable)
                .map(AssessmentMapper::toResponse);
    }

    @Override
    public UUID completeBloodTest(CreateLabTestResultDTO testResultDTO) throws NotFoundException, BadRequestException {
        Assessment assessment = assessmentRepository.findByQuestionnaireId(testResultDTO.getAssessmentId())
                .orElseThrow(() -> new NotFoundException(Map.of("detail", "Assessment not found")));

        if (assessment.getLabTestResult() != null) {
            throw new BadRequestException(Map.of("detail", "Lab test result already exists for this assessment"));
        }

        LabTestResult labTestResult = LabTestResultMapper.toEntity(testResultDTO, assessment);

        LabTestResult savedLabTest = labTestResultRepository.save(labTestResult);
        return savedLabTest.getId();

    }

    @Override
    public Page<AssessmentResponseDTO> getDonorAssessmentHistory(UUID donorId, Pageable pageable) {
        return assessmentRepository.findCompletedAssessmentsByDonorId(donorId, pageable)
                .map(AssessmentMapper::toResponse);
    }

}
