package com.nbtsms.identity_service.service.impl;

import com.nbtsms.identity_service.dto.CreateUserDTO;
import com.nbtsms.identity_service.entity.User;
import com.nbtsms.identity_service.mapper.UserMapper;
import com.nbtsms.identity_service.repository.UserRepository;
import com.nbtsms.identity_service.service.UserService;
import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.ConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String create(CreateUserDTO createUserDTO) throws BadRequestException, ConflictException {
        Map<String, String> conflictMessages = new HashMap<>();

        try {
            User user = UserMapper.toEntity(createUserDTO);
            userRepository.save(user);
            return "User created successfully";
        } catch (DataIntegrityViolationException e) {
            logger.error("User already exists or data integrity violation.", e);

            String errorMessage = e.getMessage().toLowerCase();

            if (errorMessage.contains("duplicate key")) {
                if (errorMessage.contains("email")) {
                    conflictMessages.put("email", "Email already exists.");
                }
                if (errorMessage.contains("phone_number")) {
                    conflictMessages.put("phoneNumber", "Phone number already exists.");
                }
            }

            if (!conflictMessages.isEmpty()) {
                throw new ConflictException(conflictMessages);
            }

            throw new ConflictException(Map.of("error", "Data integrity violation."));
        } catch (Exception e) {
            logger.error("An unexpected error occurred while creating the user.", e);
            throw new BadRequestException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isUserPreset(UUID id) {
        return userRepository.findById(id).isPresent();
    }
}
