package com.nbtsms.identity_service.dto;

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
public class CenterStaffDTO {
    @NotNull(message = "Staff is mandatory")
    private UUID staffId;
}
