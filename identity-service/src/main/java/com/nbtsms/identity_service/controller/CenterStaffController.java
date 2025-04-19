package com.nbtsms.identity_service.controller;

import com.nbtsms.identity_service.config.AuthUtil;
import com.nbtsms.identity_service.dto.CenterStaffDTO;
import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.ConflictException;
import com.nbtsms.identity_service.exception.NotFoundException;
import com.nbtsms.identity_service.openapi.DetailMessageResponse;
import com.nbtsms.identity_service.service.impl.CenterStaffServiceImpl;
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
@RequestMapping("staffs")
public class CenterStaffController {
    private final CenterStaffServiceImpl centerStaffService;

    public CenterStaffController(CenterStaffServiceImpl centerStaffService) {
        this.centerStaffService = centerStaffService;
    }

    @PatchMapping("{centerId}/add-staff")
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
    public ResponseEntity<Map<String, Object>> addStaffToCenter(@PathVariable UUID centerId, @Valid CenterStaffDTO centerStaffDTO) {
        Map<String, Object> response = new HashMap<>();

        UUID adminId = AuthUtil.getAuthenticatedUserId();

        if (adminId == null) {
            response.put("detail", "Unauthorised");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            centerStaffService.addStaffToCenter(centerId, centerStaffDTO.getStaffId(), adminId);
            response.put("detail", "Successfully staff added to the center");
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

    @PatchMapping("{centerId}/remove-staff")
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
                                    implementation = DetailMessageResponse.class
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
    public ResponseEntity<Map<String, Object>> removeStaffFromCenter(@PathVariable UUID centerId, @Valid CenterStaffDTO centerStaffDTO) {
        Map<String, Object> response = new HashMap<>();

        UUID adminId = AuthUtil.getAuthenticatedUserId();

        if (adminId == null) {
            response.put("detail", "Unauthorised");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            centerStaffService.removeStaffFromCenter(centerId, centerStaffDTO.getStaffId(), adminId);
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
