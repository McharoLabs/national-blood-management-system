package com.management.nationalblood.meeting.mapper;

import com.management.nationalblood.meeting.dto.ReasonForDeferralResponseDTO;
import com.management.nationalblood.meeting.entity.ReasonForDeferral;

public class ReasonForDeferralMapper {
    public static ReasonForDeferralResponseDTO toResponse(ReasonForDeferral reason) {
        return ReasonForDeferralResponseDTO.builder()
                .medicalReasons(reason.getMedicalReasons())
                .socialReasonComment(reason.getSocialReasonComment())
                .socialReasons(reason.getSocialReasons())
                .medicalOtherReasonComment(reason.getMedicalOtherReasonComment())
                .build();
    }
}
