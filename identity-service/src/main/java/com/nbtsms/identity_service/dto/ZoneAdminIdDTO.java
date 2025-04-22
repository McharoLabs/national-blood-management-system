package com.nbtsms.identity_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ZoneAdminIdDTO {
    @NotNull(message = "Admin Id is mandatory")
    private UUID adminId;
}
