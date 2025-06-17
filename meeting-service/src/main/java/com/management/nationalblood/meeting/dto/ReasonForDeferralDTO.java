package com.management.nationalblood.meeting.dto;


import com.management.nationalblood.meeting.enums.MedicalReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReasonForDeferralDTO {
    private List<MedicalReason> medicalReasons;
    private String medicalOtherReasonComment;

    private List<String> socialReasons;
    private String socialReasonComment;
}
