package com.nbtsms.zone_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ZoneAdminIdDTO {
    @NotNull
    private UUID adminId;
}
