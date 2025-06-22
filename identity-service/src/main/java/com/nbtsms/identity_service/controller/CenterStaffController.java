package com.nbtsms.identity_service.controller;

import com.nbtsms.identity_service.config.AuthUtil;
import com.nbtsms.identity_service.dto.CenterStaffDTO;
import com.nbtsms.identity_service.dto.ErrorResponseDTO;
import com.nbtsms.identity_service.dto.IdentityResponseDTO;
import com.nbtsms.identity_service.dto.UserDTO;
import com.nbtsms.identity_service.service.impl.CenterStaffServiceImpl;
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

import java.util.List;
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

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    @Operation(
            summary = "Retrieve Center Staffs",
            description = "Helps to find all center staffs by given centerId"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Center staffs retrieved successfully",
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
    public ResponseEntity<IdentityResponseDTO<List<UserDTO>>> getCenterStaffs(
            @PathVariable UUID centerId,
            HttpServletRequest request
    ) {
        IdentityResponseDTO<List<UserDTO>> response = IdentityResponseDTO.ok(
                centerStaffService.centerStaffs(centerId),
                "Staffs retrieved successfully",
                request.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("add")
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
    public ResponseEntity<IdentityResponseDTO<Map<String, String>>> addStaffToCenter(
            @PathVariable UUID centerId,
            @Valid @RequestBody CenterStaffDTO centerStaffDTO,
            HttpServletRequest request
    ) {

        centerStaffService.addStaffToCenter(centerId, centerStaffDTO.getStaffId());
        IdentityResponseDTO<Map<String, String>> response = IdentityResponseDTO.ok(null, "Successfully staff added to the center", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("remove")
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
    public ResponseEntity<IdentityResponseDTO<Map<String, String>>> removeStaffFromCenter(
            @PathVariable UUID centerId,
            @Valid @RequestBody CenterStaffDTO centerStaffDTO,
            HttpServletRequest request
    ) {
        centerStaffService.removeStaffFromCenter(centerId, centerStaffDTO.getStaffId());

        IdentityResponseDTO<Map<String, String>> response = IdentityResponseDTO.ok(null, "Successfully user unassigned from center", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
