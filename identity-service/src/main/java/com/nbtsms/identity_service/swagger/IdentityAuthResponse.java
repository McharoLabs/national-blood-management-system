package com.nbtsms.identity_service.swagger;

import com.nbtsms.identity_service.dto.IdentityResponseDTO;
import com.nbtsms.identity_service.dto.JwtAuthenticationResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "StaffAuthenticationWrapper", description = "Wrapped authentication token")
public class IdentityAuthResponse extends IdentityResponseDTO<JwtAuthenticationResponseDTO> {
}
