package com.management.nationalblood.meeting.dto;

import com.management.nationalblood.meeting.enums.DonorStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinalDonorEvaluationResponseDTO {

    private UUID id;

    private DonorStatus donorStatus;

    private ReasonForDeferralResponseDTO reasonForDeferral;

    private String comment;

    private UUID counselorId;
}
