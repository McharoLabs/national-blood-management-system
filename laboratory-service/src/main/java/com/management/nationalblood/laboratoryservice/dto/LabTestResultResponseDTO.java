package com.management.nationalblood.laboratoryservice.dto;

import com.management.nationalblood.laboratoryservice.enums.ABOGroup;
import com.management.nationalblood.laboratoryservice.enums.RhFactor;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabTestResultResponseDTO {
    private UUID id;

    private UUID assessmentId;

    private boolean hivTestResult;

    private boolean hepatitisBTestResult;

    private boolean hepatitisCTestResult;

    private boolean syphilisTestResult;

    private boolean malariaTestResult;

    private ABOGroup aboGroup;

    private RhFactor rhFactor;

    private String labTechnicianName;

    private String notes;

    private LocalDateTime testedAt;

    private LocalDateTime recordedAt;

    private boolean submittedToDonor;
}
