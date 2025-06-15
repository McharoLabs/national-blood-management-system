package com.nbtsms.identity_service.service;


import com.nbtsms.identity_service.dto.UserDTO;
import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.ConflictException;
import com.nbtsms.identity_service.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface CenterStaffService {
    void addStaffToCenter(UUID centerId, UUID staffId) throws NotFoundException, ConflictException, BadRequestException;
    void removeStaffFromCenter(UUID centerId, UUID staffId) throws NotFoundException, BadRequestException;
    List<UserDTO> centerStaffs(UUID centerId);
}
