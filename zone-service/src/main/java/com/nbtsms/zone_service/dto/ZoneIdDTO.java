package com.nbtsms.zone_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ZoneIdDTO {
    @NotNull(message = "Zone ID is mandatory")
    private UUID zoneId;
}
