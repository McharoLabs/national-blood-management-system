package com.management.nationalblood.laboratoryservice.mapper;

import com.management.nationalblood.laboratoryservice.dto.HaematologicalTestResponseDTO;
import com.management.nationalblood.laboratoryservice.entity.HaematologicalTest;

public class HaematologicalTestMapper {
    public static HaematologicalTestResponseDTO toResponse(HaematologicalTest entity) {
        return HaematologicalTestResponseDTO.builder()
                .haemoglobinLevel(entity.getHaemoglobinLevel())
                .haematocrit(entity.getHaematocrit())
                .plateletsCount(entity.getPlateletsCount())
                .serumProteinStatus(entity.getSerumProteinStatus())
                .build();
    }

    public static HaematologicalTest toEntity(HaematologicalTestResponseDTO dto) {
        if (dto == null) return null;

        return HaematologicalTest.builder()
                .haemoglobinLevel(dto.getHaemoglobinLevel())
                .haematocrit(dto.getHaematocrit())
                .plateletsCount(dto.getPlateletsCount())
                .serumProteinStatus(dto.getSerumProteinStatus())
                .measuredBy(dto.getMeasuredById())
                .build();
    }
}
