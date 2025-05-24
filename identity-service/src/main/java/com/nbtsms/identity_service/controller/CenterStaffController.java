package com.nbtsms.identity_service.controller;

import com.nbtsms.identity_service.config.AuthUtil;
import com.nbtsms.identity_service.dto.CenterStaffDTO;
import com.nbtsms.identity_service.dto.IdentityResponseDTO;
import com.nbtsms.identity_service.service.impl.CenterStaffServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Tag(name = "Center Staff", description = "Endpoints related to center staffs")
@RestController
@RequestMapping("centers/{centerId}/staff")
public class CenterStaffController {
    private final CenterStaffServiceImpl centerStaffService;

    public CenterStaffController(CenterStaffServiceImpl centerStaffService) {
        this.centerStaffService = centerStaffService;
    }

    @PatchMapping("add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(
            summary = "Add staff to center",
            description = "Add staff to the center"
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
                                    implementation = ErrorMessage.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            ),
    })
    public ResponseEntity<IdentityResponseDTO<Map<String, String>>> addStaffToCenter(
            @PathVariable UUID centerId,
            @Valid @RequestBody CenterStaffDTO centerStaffDTO,
            HttpServletRequest request
    ) {

        UUID adminId = AuthUtil.getAuthenticatedUserId();

        if (adminId == null) {
            IdentityResponseDTO<Map<String, String>> response = IdentityResponseDTO.error(403, "You are forbidden to make these changes", request.getRequestURI());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        centerStaffService.addStaffToCenter(centerId, centerStaffDTO.getStaffId(), adminId);
        IdentityResponseDTO<Map<String, String>> response = IdentityResponseDTO.ok(null, "Successfully staff added to the center", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("remove")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(
            summary = "Remove staff from center",
            description = "Remove staff from the center"
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
                                    implementation = ErrorMessage.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            ),
    })
    public ResponseEntity<IdentityResponseDTO<Map<String, String>>> removeStaffFromCenter(
            @PathVariable UUID centerId,
            @Valid @RequestBody CenterStaffDTO centerStaffDTO,
            HttpServletRequest request
    ) {
        UUID adminId = AuthUtil.getAuthenticatedUserId();

        if (adminId == null) {
            IdentityResponseDTO<Map<String, String>> response = IdentityResponseDTO.error(403, "You are forbidden to make these changes", request.getRequestURI());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        centerStaffService.removeStaffFromCenter(centerId, centerStaffDTO.getStaffId(), adminId);

        IdentityResponseDTO<Map<String, String>> response = IdentityResponseDTO.ok(null, "Successfully user unassigned from center", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
