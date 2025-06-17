package com.management.nationalblood.meeting.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PhysicalExaminationDTO {
    @NotNull private Double weightKg;
    @NotNull private Double heightCm;
    @NotNull private String scaleUsedToMeasure;
    @NotNull private UUID questionnaireId;
}
