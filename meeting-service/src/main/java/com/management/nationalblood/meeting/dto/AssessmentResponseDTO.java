package com.management.nationalblood.meeting.dto;

import com.management.nationalblood.meeting.enums.FormProgress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentResponseDTO {
    private UUID id;

    private DonorResponseDTO donor;

    private UUID meetingId;
    private String meetingTitle;

    private HaematologicalTestResponseDTO haematologicalTest;


    private BloodCollectionDataResponseDTO bloodCollectionData;
    private AdverseEventResponseDTO adverseEvent;

    private FinalDonorEvaluationResponseDTO finalDonorEvaluation;

    private FormProgress formProgress;
    private LocalDateTime formStartedAt;
    private LocalDateTime lastUpdatedAt;
}