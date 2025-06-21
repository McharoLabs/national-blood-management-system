package com.management.nationalblood.laboratoryservice.dto;

import com.management.nationalblood.laboratoryservice.enums.ABOGroup;
import com.management.nationalblood.laboratoryservice.enums.RhFactor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLabTestResultDTO {
    @NotNull(message = "Assessment ID is required")
    private UUID assessmentId;

    @NotNull(message = "HIV test result is required")
    private Boolean hivTestResult;

    @NotNull(message = "Hepatitis B test result is required")
    private Boolean hepatitisBTestResult;

    @NotNull(message = "Hepatitis C test result is required")
    private Boolean hepatitisCTestResult;

    @NotNull(message = "Syphilis test result is required")
    private Boolean syphilisTestResult;

    @NotNull(message = "Malaria test result is required")
    private Boolean malariaTestResult;

    @NotNull(message = "ABO blood group is required")
    private ABOGroup aboGroup;

    @NotNull(message = "Rh factor is required")
    private RhFactor rhFactor;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;

    @NotNull(message = "Tested at timestamp is required")
    private LocalDateTime testedAt;
}
