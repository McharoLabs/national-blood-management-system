package com.nbts.management.donor_service.dto;

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
    private List<String> medicalReasons;
    private String medicalOtherReasonComment;
    private List<String> socialReasons;
    private String socialReasonComment;

}
