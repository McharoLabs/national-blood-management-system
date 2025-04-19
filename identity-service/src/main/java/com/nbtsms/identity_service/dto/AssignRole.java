package com.nbtsms.identity_service.dto;

import com.nbtsms.identity_service.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AssignRole {
    @NotNull(message = "No roles selected")
    private List<Role> roles;
}
