package com.nbtsms.zone_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateZoneDTO {
    @NotBlank(message = "Zone name can not be blank")
    private String name;
}
