package com.management.nationalblood.meeting.mapper;

import com.management.nationalblood.meeting.dto.FinalDonorEvaluationDTO;
import com.management.nationalblood.meeting.dto.FinalDonorEvaluationResponseDTO;
import com.management.nationalblood.meeting.entity.FinalDonorEvaluation;
import com.management.nationalblood.meeting.entity.ReasonForDeferral;
import com.management.nationalblood.meeting.enums.DonorStatus;
import com.management.nationalblood.meeting.exception.BadRequestException;

import java.util.Map;

public class FinalDonorEvaluationMapper {
    public static FinalDonorEvaluation toEntity(FinalDonorEvaluationDTO dto) {
        if (dto == null) return null;

        FinalDonorEvaluation entity = new FinalDonorEvaluation();

        entity.setDonorStatus(dto.getDonorStatus());

        if (dto.getDonorStatus() == DonorStatus.TEMPORARILY_DEFERRED ||
                dto.getDonorStatus() == DonorStatus.PERMANENTLY_DEFERRED) {

            if (dto.getReasonForDeferral() == null) {
                throw new BadRequestException(Map.of("detail", "Reason for deferral is required when donor is temporarily or permanently deferred."));
            }

            ReasonForDeferral reason = new ReasonForDeferral(
                    dto.getReasonForDeferral().getMedicalReasons(),
                    dto.getReasonForDeferral().getMedicalOtherReasonComment(),
                    dto.getReasonForDeferral().getSocialReasons(),
                    dto.getReasonForDeferral().getSocialReasonComment()
            );
            entity.setReasonForDeferral(reason);
        } else {
            entity.setReasonForDeferral(null);
        }

        entity.setComment(dto.getComment());

        return entity;
    }


    public static FinalDonorEvaluationResponseDTO toResponse(FinalDonorEvaluation evaluation) {
        return FinalDonorEvaluationResponseDTO.builder()
                .id(evaluation.getId())
                .comment(evaluation.getComment())
                .donorStatus(evaluation.getDonorStatus())
                .reasonForDeferral(
                        evaluation.getReasonForDeferral() != null
                                ? ReasonForDeferralMapper.toResponse(evaluation.getReasonForDeferral())
                                : null
                )
                .build();
    }

}
