package com.nbtsms.zone_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CenterStaffResponseDTO {
    private UUID staffId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
