package com.nbtsms.zone_service.mapper;

import com.nbtsms.zone_service.dto.AdminResponseDTO;
import com.nbtsms.zone_service.entity.Admin;
import com.nbtsms.zone_service.event.AdminZoneAssignmentEvent;

public class AdminMapper {
    public static Admin toEntity(AdminZoneAssignmentEvent event) {
        Admin admin = new Admin();

        admin.setFirstName(event.getFirstName());
        admin.setMiddleName(event.getMiddleName());
        admin.setLastName(event.getLastName());
        admin.setEmail(event.getEmail());
        admin.setPhoneNumber(event.getPhoneNumber());
        admin.setAdminId(event.getAdminId());

        return admin;
    }

    public static AdminResponseDTO toResponse(Admin admin) {
        AdminResponseDTO responseDTO = new AdminResponseDTO();

        responseDTO.setAdminId(admin.getAdminId());
        responseDTO.setFirstName(admin.getFirstName());
        responseDTO.setMiddleName(admin.getMiddleName());
        responseDTO.setLastName(admin.getLastName());
        responseDTO.setEmail(admin.getEmail());
        responseDTO.setPhoneNumber(admin.getPhoneNumber());

        return responseDTO;
    }
}
