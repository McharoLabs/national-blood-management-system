package com.nbtsms.zone_service.mapper;

import com.nbtsms.zone_service.dto.CenterStaffResponseDTO;
import com.nbtsms.zone_service.entity.Staff;
import com.nbtsms.zone_service.event.StaffCenterAssignmentEvent;

public class CenterStaffMapper {
    public static Staff toEntity(StaffCenterAssignmentEvent event) {
        Staff staff = new Staff();

        staff.setFirstName(event.getFirstName());
        staff.setMiddleName(event.getMiddleName());
        staff.setLastName(event.getLastName());
        staff.setEmail(event.getEmail());
        staff.setPhoneNumber(event.getPhoneNumber());
        staff.setStaffId(event.getStaffId());

        return staff;
    }

    public static CenterStaffResponseDTO toResponse(Staff staff) {
        CenterStaffResponseDTO responseDTO = new CenterStaffResponseDTO();

        responseDTO.setStaffId(staff.getStaffId());
        responseDTO.setFirstName(staff.getFirstName());
        responseDTO.setMiddleName(staff.getMiddleName());
        responseDTO.setLastName(staff.getLastName());
        responseDTO.setEmail(staff.getEmail());
        responseDTO.setPhoneNumber(staff.getPhoneNumber());

        return responseDTO;
    }
}
