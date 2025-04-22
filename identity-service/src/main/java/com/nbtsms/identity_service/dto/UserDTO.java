package com.nbtsms.identity_service.dto;

import com.nbtsms.identity_service.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<Role> roles;
    private UUID zoneId;
    private UUID centerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
