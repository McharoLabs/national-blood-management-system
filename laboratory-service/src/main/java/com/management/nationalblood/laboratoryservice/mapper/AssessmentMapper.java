package com.management.nationalblood.laboratoryservice.mapper;

import com.management.nationalblood.laboratoryservice.dto.AssessmentResponseDTO;
import com.management.nationalblood.laboratoryservice.dto.BloodCollectionDataResponseDTO;
import com.management.nationalblood.laboratoryservice.dto.HaematologicalTestResponseDTO;
import com.management.nationalblood.laboratoryservice.entity.Assessment;
import com.management.nationalblood.laboratoryservice.entity.BloodCollectionData;
import com.management.nationalblood.laboratoryservice.entity.HaematologicalTest;
import com.management.nationalblood.laboratoryservice.event.QuestionnaireCompletedEvent;

public class AssessmentMapper {
    public static Assessment toEntity(QuestionnaireCompletedEvent event) {
        BloodCollectionDataResponseDTO bloodData = event.getBloodCollectionData();
        HaematologicalTestResponseDTO testData = event.getHaematologicalTest();

        // Create parent Assessment first
        Assessment assessment = Assessment.builder()
                .questionnaireId(event.getQuestionnaireId())
                .donorId(event.getDonorId())
                .meetingId(event.getMeetingId())
                .centerId(event.getCenterId())
                .build();

        // Create BloodCollectionData and link it
        BloodCollectionData bloodCollectionData = BloodCollectionData.builder()
                .assessment(assessment) // important for bidirectional link
                .timeNeedleInserted(bloodData.getTimeNeedleInserted())
                .timeNeedleRemoved(bloodData.getTimeNeedleRemoved())
                .venipunctureSuccessful(bloodData.isVenipunctureSuccessful())
                .bloodCollectionUnsuccessful(bloodData.isBloodCollectionUnsuccessful())
                .smallAmountCollected(bloodData.getSmallAmountCollected())
                .quantityOfBloodCollected(bloodData.getQuantityOfBloodCollected())
                .scaleUsed(bloodData.getScaleUsed())
                .apheresisMachineUsed(bloodData.getApheresisMachineUsed())
                .bloodBagType(bloodData.getBloodBagType())
                .bloodBagLotNumber(bloodData.getBloodBagLotNumber())
                .bloodBagExpiryDate(bloodData.getBloodBagExpiryDate())
                .bloodProductType(bloodData.getBloodProductType())
                .build();

        // Create HaematologicalTest and link it
        HaematologicalTest haematologicalTest = HaematologicalTest.builder()
                .assessment(assessment) // important for bidirectional link
                .haemoglobinLevel(testData.getHaemoglobinLevel())
                .haematocrit(testData.getHaematocrit())
                .plateletsCount(testData.getPlateletsCount())
                .serumProteinStatus(testData.getSerumProteinStatus())
                .build();

        // Attach child entities to parent
        assessment.setBloodCollectionData(bloodCollectionData);
        assessment.setHaematologicalTest(haematologicalTest);

        return assessment;
    }

    public static AssessmentResponseDTO toResponse(Assessment assessment) {
        return AssessmentResponseDTO.builder()
                .id(assessment.getId())
                .questionnaireId(assessment.getQuestionnaireId())
                .donorId(assessment.getDonorId())
                .meetingId(assessment.getMeetingId())
                .centerId(assessment.getCenterId())
                .bloodCollectionData(BloodCollectionDataMapper.toResponse(assessment.getBloodCollectionData()))
                .haematologicalTest(HaematologicalTestMapper.toResponse(assessment.getHaematologicalTest()))
                .labTestResult(
                        assessment.getLabTestResult() != null ?
                                LabTestResultMapper.toResponse(assessment.getLabTestResult()) : null
                )
                .build();
    }


}
