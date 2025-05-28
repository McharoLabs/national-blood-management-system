package com.nbtsms.identity_service.service.impl;

import com.nbtsms.identity_service.dto.AssignRole;
import com.nbtsms.identity_service.dto.CreateUserDTO;
import com.nbtsms.identity_service.dto.UserDTO;
import com.nbtsms.identity_service.entity.User;
import com.nbtsms.identity_service.enums.Role;
import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.ConflictException;
import com.nbtsms.identity_service.exception.NotFoundException;
import com.nbtsms.identity_service.mapper.AuthenticationMapper;
import com.nbtsms.identity_service.mapper.UserMapper;
import com.nbtsms.identity_service.repository.UserRepository;
import com.nbtsms.identity_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UUID create(CreateUserDTO createUserDTO) throws ConflictException, BadRequestException {
        Optional<User> existingUserByEmail = userRepository.findByEmail(createUserDTO.getEmail());
        Optional<User> existingUserByPhoneNumber = userRepository.findByPhoneNumber(createUserDTO.getPhoneNumber());
        Map<String, String> errors = new HashMap<>();

        if (existingUserByEmail.isPresent()) {
            errors.put("email", "Staff with given email, already exists.");

        }

        if (existingUserByPhoneNumber.isPresent()) {
            errors.put("phoneNumber", "Staff with this phone number already exists.");
        }

        if (!errors.isEmpty()) {
            throw new ConflictException(errors);
        }

        User user = AuthenticationMapper.toEntity(createUserDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(Role.USER));

        try {
            User savedUser = userRepository.save(user);
            return savedUser.getId();
        } catch (Exception e) {
            logger.error("An unexpected error occurred.", e);
            throw e;
        }
    }

    @Override
    public Page<UserDTO> getUsers(String name, Pageable pageable) {
        return userRepository.findAllStaffs(name, List.of(Role.COUNSELOR, Role.LAB_TECHNICIAN, Role.ADMIN, Role.USER), pageable)
                .map(UserMapper::toResponse);
    }

    @Override
    public Page<UserDTO> getAllAdmin(String name, Pageable pageable) {

        return userRepository.findStaffsByRoles(name, List.of(Role.ADMIN), pageable)
                .map(UserMapper::toResponse);
    }

    @Override
    public Page<UserDTO> searchUsers(String name, String email, Pageable pageable) {
        return userRepository.fetchSearchUsers(name, email, pageable)
                .map(UserMapper::toResponse);
    }

    @Override
    public Optional<User> getUser(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public void assignRole(AssignRole assignRole, UUID userId) throws NotFoundException, BadRequestException {
        Map<String, String> errors = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            errors.put("user", "User not found");
            throw new NotFoundException(errors);
        }

        Set<Role> currentRoles = new HashSet<>(user.getRoles());

        Set<Role> incomingRoles = new HashSet<>(assignRole.getRoles());
        incomingRoles.add(Role.USER);

        if (incomingRoles.contains(Role.SUPER_USER)) {
            errors.put("roles", "Cannot assign super admin role.");
            throw new BadRequestException(errors);
        }

        if (currentRoles.equals(incomingRoles)) {
            errors.put("roles", "No changes were made. Same roles already assigned.");
            throw new BadRequestException(errors);
        }

        user.setRoles(new ArrayList<>(incomingRoles));
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> availableStaffs(String name) {
        return userRepository.fetchAvailableStaff(name)
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> availableAdmin(String name) {
        return userRepository.fetchAvailableAdmin(name)
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean staffExists(UUID staffId) {
        return userRepository.findById(staffId).isPresent();
    }

    @Override
    public UUID getZoneId(UUID staffId) {
        return userRepository.findById(staffId)
                .map(User::getZoneId)
                .orElse(null);
    }

}
