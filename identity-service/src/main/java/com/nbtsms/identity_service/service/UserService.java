package com.nbtsms.identity_service.service;

import com.nbtsms.identity_service.dto.AssignRole;
import com.nbtsms.identity_service.dto.CreateUserDTO;
import com.nbtsms.identity_service.dto.UpdateUserDTO;
import com.nbtsms.identity_service.dto.UserDTO;
import com.nbtsms.identity_service.entity.User;
import com.nbtsms.identity_service.event.StaffAssignedEvent;
import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.ConflictException;
import com.nbtsms.identity_service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    UUID create(CreateUserDTO createUserDTO) throws ConflictException, BadRequestException;
    Page<UserDTO> getUsers(String name, Pageable pageable);
    Page<UserDTO> getAllAdmin(String name, Pageable pageable);
    UserDTO getUser(UUID id) throws NotFoundException;
    void assignRole(AssignRole assignRole, UUID userId) throws NotFoundException, BadRequestException;
    boolean staffExists(UUID staffId);
    UUID getZoneId(UUID staffId);
    Page<UserDTO> searchUsers(String name, String email, Pageable pageable);
    List<UserDTO> availableStaffs(String name);
    List<UserDTO> availableAdmin(String name);
    UUID update(UpdateUserDTO updateUserDTO) throws NotFoundException, BadRequestException;
    void assignedStaffToMeeting(StaffAssignedEvent event);
}
