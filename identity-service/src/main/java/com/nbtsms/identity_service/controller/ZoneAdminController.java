package com.nbtsms.identity_service.controller;

import com.nbtsms.identity_service.dto.ZoneAdminIdDTO;
import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.ConflictException;
import com.nbtsms.identity_service.exception.NotFoundException;
import com.nbtsms.identity_service.openapi.DetailMessageResponse;
import com.nbtsms.identity_service.openapi.FieldErrorResponse;
import com.nbtsms.identity_service.service.impl.ZoneAdminServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("users/centers/admin")
public class ZoneAdminController {
    private final ZoneAdminServiceImpl zoneAdminService;

    public ZoneAdminController(ZoneAdminServiceImpl zoneAdminService) {
        this.zoneAdminService = zoneAdminService;
    }

    @PatchMapping("{zoneId}/assign-admin")
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
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
                                    implementation = DetailMessageResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = DetailMessageResponse.class,
                                    description = "User | Zone not found"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DetailMessageResponse.class)
                    )
            ),
    })
    public ResponseEntity<Map<String, Object>> assignZoneAdmin(@PathVariable UUID zoneId, @Valid ZoneAdminIdDTO zoneAdminIdDTO) {
        Map<String, Object> response = new HashMap<>();

        try {
            zoneAdminService.assignAdminToZone(zoneId, zoneAdminIdDTO.getAdminId());
            response.put("detail", "Successfully admin assigned to zone");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (ConflictException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (BadRequestException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("detail", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{zoneId}/unassign-admin")
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
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
                                    implementation = DetailMessageResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = FieldErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DetailMessageResponse.class)
                    )
            ),
    })
    public ResponseEntity<Map<String, Object>> unAssignZoneAdmin(@PathVariable UUID zoneId, @Valid ZoneAdminIdDTO zoneAdminIdDTO) {
        Map<String, Object> response = new HashMap<>();

        try {
            zoneAdminService.removeAdminFromZone(zoneId, zoneAdminIdDTO.getAdminId());
            response.put("detail", "Successfully user unassigned from zone");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            response.putAll(e.getErrorMessages());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.put("detail", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
