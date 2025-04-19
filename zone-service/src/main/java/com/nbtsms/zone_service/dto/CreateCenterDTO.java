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
public class CreateCenterDTO {
    @NotBlank(message = "Center name is mandatory")
    private String name;

    @NotBlank(message = "Center address is mandatory")
    private String address;

    @NotNull(message = "Center region is mandatory")
    private UUID regionId;
}
