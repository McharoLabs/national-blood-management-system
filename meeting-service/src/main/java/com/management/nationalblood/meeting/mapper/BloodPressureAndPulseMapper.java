package com.management.nationalblood.meeting.mapper;


import com.management.nationalblood.meeting.dto.BloodPressureAndPulseDTO;
import com.management.nationalblood.meeting.entity.BloodPressureAndPulse;

public class BloodPressureAndPulseMapper {
    public static BloodPressureAndPulse toEntity(BloodPressureAndPulseDTO dto) {
        BloodPressureAndPulse bp = new BloodPressureAndPulse();
        bp.setPulse(dto.getPulse());
        bp.setBloodPressure(dto.getBloodPressure());
        bp.setBpMachine(dto.getBpMachine());
        return bp;
    }
}
