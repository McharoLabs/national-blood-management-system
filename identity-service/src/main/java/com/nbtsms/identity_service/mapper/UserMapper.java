package com.nbtsms.identity_service.mapper;

import com.nbtsms.identity_service.dto.CreateUserDTO;
import com.nbtsms.identity_service.dto.RoleDTO;
import com.nbtsms.identity_service.dto.UserDTO;
import com.nbtsms.identity_service.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toEntity(CreateUserDTO createUserDTO) {
        User user = new User();

        user.setFirstName(createUserDTO.getFirstName());
        user.setMiddleName(createUserDTO.getMiddleName());
        user.setLastName(createUserDTO.getLastName());
        user.setEmail(createUserDTO.getEmail());
        user.setPhoneNumber(createUserDTO.getPhoneNumber());
        user.setPassword(createUserDTO.getPassword());

        return user;
    }

    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());

        Set<RoleDTO> roleDTOs = user.getRoles().stream()
                .map(userRole -> new RoleDTO(userRole.getRole().getId(), userRole.getRole().getName()))
                .collect(Collectors.toSet());

        userDTO.setRoles(roleDTOs);

        return userDTO;
    }
}
