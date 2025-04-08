package com.nbtsms.zone_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AssignZoneAdminDTO {
    @NotNull(message = "Admin ID is required")
    private UUID adminId;
}
