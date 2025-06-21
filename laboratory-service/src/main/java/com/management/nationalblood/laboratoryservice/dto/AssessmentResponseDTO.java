package com.management.nationalblood.laboratoryservice.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentResponseDTO {
    private UUID id;
    private UUID questionnaireId;
    private UUID donorId;
    private UUID meetingId;
    private UUID centerId;

    // Nest objects, not flatten
    private BloodCollectionDataResponseDTO bloodCollectionData;
    private HaematologicalTestResponseDTO haematologicalTest;
    private LabTestResultResponseDTO labTestResult;
}
