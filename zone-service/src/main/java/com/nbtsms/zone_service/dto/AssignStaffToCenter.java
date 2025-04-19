package com.nbtsms.zone_service.dto;

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
public class AssignStaffToCenter {
    @NotNull(message = "Staff ID is mandatory")
    private UUID staffId;
}
