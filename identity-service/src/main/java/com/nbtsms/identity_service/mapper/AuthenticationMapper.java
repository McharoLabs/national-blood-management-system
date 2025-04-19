package com.nbtsms.identity_service.mapper;


import com.nbtsms.identity_service.dto.CreateUserDTO;
import com.nbtsms.identity_service.entity.User;

public class AuthenticationMapper {
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
}
