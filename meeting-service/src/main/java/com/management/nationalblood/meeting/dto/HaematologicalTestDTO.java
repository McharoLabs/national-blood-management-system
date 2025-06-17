package com.management.nationalblood.meeting.dto;

import com.management.nationalblood.meeting.enums.SerumProteinStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class HaematologicalTestDTO {
    @NotNull private Double haemoglobinLevel;
    @NotNull private Double haematocrit;
    @NotNull private Integer plateletsCount;
    @NotNull private SerumProteinStatus serumProteinStatus;
    @NotNull private UUID questionnaireId;
}