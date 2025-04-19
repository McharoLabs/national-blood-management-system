package com.nbtsms.identity_service.service;


import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.ConflictException;
import com.nbtsms.identity_service.exception.NotFoundException;

import java.util.UUID;

public interface ZoneAdminService {
    void assignAdminToZone(UUID zoneId, UUID adminId) throws NotFoundException, ConflictException, BadRequestException;
    void removeAdminFromZone(UUID zoneId, UUID adminId) throws NotFoundException, BadRequestException;
}
