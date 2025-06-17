package com.management.nationalblood.meeting.dto;

import com.management.nationalblood.meeting.enums.SerumProteinStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HaematologicalTestResponseDTO {

    private UUID id;

    private Double haemoglobinLevel;

    private Double haematocrit;

    private Integer plateletsCount;

    private SerumProteinStatus serumProteinStatus;

    private UUID measuredById;

    private String measuredByFullName;
}
