package com.nbtsms.zone_service.service;

import com.nbtsms.zone_service.dto.CreateCenterDTO;
import com.nbtsms.zone_service.exception.ConflictException;
import com.nbtsms.zone_service.exception.NotFoundException;

public interface CenterService {
    void create(CreateCenterDTO createCenterDTO) throws ConflictException, NotFoundException;
}
