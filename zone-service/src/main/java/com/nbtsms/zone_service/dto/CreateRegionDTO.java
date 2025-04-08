package com.nbtsms.zone_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRegionDTO {
    @NotBlank(message = "Region name can not be blank")
    private String name;

    @NotNull(message = "Zone for the region is mandatory")
    private UUID zoneId;
}
