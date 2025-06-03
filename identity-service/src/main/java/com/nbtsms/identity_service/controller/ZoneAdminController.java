package com.nbtsms.identity_service.controller;

import com.nbtsms.identity_service.dto.ErrorResponseDTO;
import com.nbtsms.identity_service.dto.IdentityResponseDTO;
import com.nbtsms.identity_service.dto.ZoneAdminIdDTO;
import com.nbtsms.identity_service.service.impl.ZoneAdminServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Tag(name = "Zone Admin", description = "Endpoints related to zone admins")
@RestController
@RequestMapping("zones/{zoneId}/admins")
public class ZoneAdminController {
    private final ZoneAdminServiceImpl zoneAdminService;

    public ZoneAdminController(ZoneAdminServiceImpl zoneAdminService) {
        this.zoneAdminService = zoneAdminService;
    }

    @PostMapping("assign")
    @PreAuthorize("hasAuthority('ROLE_SUPER_USER')")
    @Operation(
            summary = "Assign admin to zone",
            description = "Assign user to zone as admin"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = IdentityResponseDTO.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponseDTO.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponseDTO.class
                            )
                    )
            ),
    })
    public ResponseEntity<IdentityResponseDTO<Map<String, Object>>> assignZoneAdmin(
            @PathVariable UUID zoneId,
            @Valid @RequestBody ZoneAdminIdDTO zoneAdminIdDTO,
            HttpServletRequest request
    ) {
        zoneAdminService.assignAdminToZone(zoneId, zoneAdminIdDTO.getAdminId());
        IdentityResponseDTO<Map<String, Object>> response = IdentityResponseDTO.ok(null, "Successfully admin assigned to zone", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("remove")
    @PreAuthorize("hasAuthority('ROLE_SUPER_USER')")
    @Operation(
            summary = "Unassign admin from zone",
            description = "Unassign user from zone as admin"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = IdentityResponseDTO.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponseDTO.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
    })
    public ResponseEntity<IdentityResponseDTO<Map<String, Object>>> unAssignZoneAdmin(
            @PathVariable UUID zoneId,
            @Valid @RequestBody ZoneAdminIdDTO zoneAdminIdDTO,
            HttpServletRequest request
    ) {
        zoneAdminService.removeAdminFromZone(zoneId, zoneAdminIdDTO.getAdminId());
        IdentityResponseDTO<Map<String, Object>> response = IdentityResponseDTO.ok(null, "Successfully user unassigned from zone", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
