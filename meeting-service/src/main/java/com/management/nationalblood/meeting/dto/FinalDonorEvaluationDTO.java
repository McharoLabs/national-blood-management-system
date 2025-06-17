package com.management.nationalblood.meeting.dto;

import com.management.nationalblood.meeting.enums.DonorStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class FinalDonorEvaluationDTO {
    @NotNull(message = "Donor status is required")
    private DonorStatus donorStatus;


    private String comment;

    @Valid
    private ReasonForDeferralDTO reasonForDeferral;

    @NotNull(message = "Questionnaire ID is mandatory")
    private UUID questionnaireId;
}
