package com.management.nationalblood.meeting.mapper;

import com.management.nationalblood.meeting.dto.HaematologicalTestDTO;
import com.management.nationalblood.meeting.dto.HaematologicalTestResponseDTO;
import com.management.nationalblood.meeting.entity.HaematologicalTest;

public class HaematologicalTestMapper {

    public static HaematologicalTest toEntity(HaematologicalTestDTO dto) {
        HaematologicalTest test = new HaematologicalTest();
        test.setHaemoglobinLevel(dto.getHaemoglobinLevel());
        test.setHaematocrit(dto.getHaematocrit());
        test.setPlateletsCount(dto.getPlateletsCount());
        test.setSerumProteinStatus(dto.getSerumProteinStatus());
        return test;
    }


    public static HaematologicalTestResponseDTO toResponse(HaematologicalTest test) {
        return HaematologicalTestResponseDTO.builder()
                .id(test.getId())
                .haemoglobinLevel(test.getHaemoglobinLevel())
                .haematocrit(test.getHaematocrit())
                .plateletsCount(test.getPlateletsCount())
                .serumProteinStatus(test.getSerumProteinStatus())
                .build();
    }
}
