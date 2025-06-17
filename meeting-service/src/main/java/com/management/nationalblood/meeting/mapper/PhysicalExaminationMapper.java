package com.management.nationalblood.meeting.mapper;

import com.management.nationalblood.meeting.dto.PhysicalExaminationDTO;
import com.management.nationalblood.meeting.entity.PhysicalExamination;

public class PhysicalExaminationMapper {

    public static PhysicalExamination toEntity(PhysicalExaminationDTO dto) {
        PhysicalExamination exam = new PhysicalExamination();
        exam.setWeightKg(dto.getWeightKg());
        exam.setHeightCm(dto.getHeightCm());
        exam.setScaleUsedToMeasure(dto.getScaleUsedToMeasure());
        return exam;
    }

    public static PhysicalExaminationDTO toDTO(PhysicalExamination entity) {
        PhysicalExaminationDTO dto = new PhysicalExaminationDTO();
        dto.setWeightKg(entity.getWeightKg());
        dto.setHeightCm(entity.getHeightCm());
        dto.setScaleUsedToMeasure(entity.getScaleUsedToMeasure());
        return dto;
    }
}
