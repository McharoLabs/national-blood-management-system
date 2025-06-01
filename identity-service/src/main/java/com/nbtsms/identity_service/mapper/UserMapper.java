package com.nbtsms.identity_service.mapper;


import com.nbtsms.identity_service.dto.CreateUserDTO;
import com.nbtsms.identity_service.dto.UserDTO;
import com.nbtsms.identity_service.entity.User;

public class UserMapper {
    public static User toEntity(CreateUserDTO createUserDTO) {
        User user = new User();

        user.setFirstName(createUserDTO.getFirstName());
        user.setMiddleName(createUserDTO.getMiddleName());
        user.setLastName(createUserDTO.getLastName());
        user.setEmail(createUserDTO.getEmail());
        user.setPhoneNumber(createUserDTO.getPhoneNumber());
        user.setPassword(createUserDTO.getPassword());
        user.setRoles(createUserDTO.getRoles());

        return user;
    }

    public static UserDTO toResponse(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setMiddleName(user.getMiddleName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setZoneId(user.getZoneId());
        dto.setCenterId(user.getCenterId());

        return dto;
    }
}
