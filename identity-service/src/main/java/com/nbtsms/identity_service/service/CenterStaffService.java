package com.nbtsms.identity_service.service;


import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.ConflictException;
import com.nbtsms.identity_service.exception.NotFoundException;

import java.util.UUID;

public interface CenterStaffService {
    void addStaffToCenter(UUID centerId, UUID staffId, UUID adminId) throws NotFoundException, ConflictException, BadRequestException;
    void removeStaffFromCenter(UUID centerId, UUID staffId, UUID adminId) throws NotFoundException, BadRequestException;
}
