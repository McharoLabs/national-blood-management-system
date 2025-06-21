package com.management.nationalblood.laboratoryservice.service;

import com.management.nationalblood.laboratoryservice.dto.AssessmentResponseDTO;
import com.management.nationalblood.laboratoryservice.dto.CreateLabTestResultDTO;
import com.management.nationalblood.laboratoryservice.event.QuestionnaireCompletedEvent;
import com.management.nationalblood.laboratoryservice.exception.BadRequestException;
import com.management.nationalblood.laboratoryservice.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AssessmentService {
    void handleCompletedAssessmentForTest(QuestionnaireCompletedEvent event);
    Page<AssessmentResponseDTO> getAssessmentForTest(UUID centerId, String bloodBagLotNumber, Pageable pageable);
    UUID completeBloodTest(CreateLabTestResultDTO testResultDTO) throws NotFoundException, BadRequestException;
    Page<AssessmentResponseDTO> getDonorAssessmentHistory(UUID donorId, Pageable pageable);
}
