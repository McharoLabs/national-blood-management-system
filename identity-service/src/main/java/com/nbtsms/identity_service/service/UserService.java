package com.nbtsms.identity_service.service;

import com.nbtsms.identity_service.dto.CreateUserDTO;
import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.ConflictException;

import java.util.UUID;

public interface UserService {
    String create(CreateUserDTO createUserDTO) throws BadRequestException, ConflictException;
    boolean isUserPreset(UUID id);
}
