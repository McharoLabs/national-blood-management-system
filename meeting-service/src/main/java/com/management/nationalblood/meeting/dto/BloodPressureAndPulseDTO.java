package com.management.nationalblood.meeting.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class BloodPressureAndPulseDTO {
    @NotNull private Integer pulse;
    @NotNull private Integer bloodPressure;
    @NotNull private String bpMachine;
    @NotNull private UUID questionnaireId;
}