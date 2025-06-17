package com.management.nationalblood.meeting.dto;

import com.management.nationalblood.meeting.enums.MedicalReason;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReasonForDeferralResponseDTO {

    private List<MedicalReason> medicalReasons;

    private String medicalOtherReasonComment;

    private List<String> socialReasons;

    private String socialReasonComment;
}
