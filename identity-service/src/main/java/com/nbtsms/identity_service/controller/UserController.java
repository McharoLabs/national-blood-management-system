package com.nbtsms.identity_service.controller;

import com.nbtsms.identity_service.dto.AssignRole;
import com.nbtsms.identity_service.dto.CreateUserDTO;
import com.nbtsms.identity_service.dto.UserDTO;
import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.ConflictException;
import com.nbtsms.identity_service.exception.NotFoundException;
import com.nbtsms.identity_service.openapi.DetailMessageResponse;
import com.nbtsms.identity_service.openapi.FieldErrorResponse;
import com.nbtsms.identity_service.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Tag(name = "Staffs", description = "Endpoints related to staffs")
@RestController
@RequestMapping
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPER_ADMIN')")
    @Operation(
            summary = "Create a new user",
            description = "Allows admin or super admin to create a new user account."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DetailMessageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed or bad request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FieldErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User already exists (conflict)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DetailMessageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DetailMessageResponse.class)
                    )
            )
    })
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateUserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();

        try {
            UUID userId = userService.create(userDTO);
            response.put("userId", userId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
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

    @GetMapping("all-users")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @Operation(
            summary = "Get all users",
            description = "Retrieve the list of all users"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Users fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = """
                    {
                      "detail": "Something went wrong"
                    }
                """
                            )
                    )
            )
    })
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDTO> userDTOList = userService.getUsers();
            return ResponseEntity.ok(userDTOList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("detail", e.getMessage()));
        }
    }

    @GetMapping("all-admin")
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @Operation(
            summary = "Get all admin",
            description = "Retrieve the list of all admin"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "UseAdmin fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = UserDTO.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "InternalError",
                                    summary = "Server error response",
                                    value = "{\"detail\": \"Internal server error occurred.\"}"
                            ),
                            schema = @Schema(implementation = Map.class)
                    )
            )

    })
    public ResponseEntity<?> getAllAdmin() {
        try {
            List<UserDTO> userDTOList = userService.getAllAdmin();
            return ResponseEntity.ok(userDTOList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("detail", e.getMessage()));
        }
    }

    @PatchMapping("{userId}/assign-roles")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @Operation(
            summary = "Assign role to user",
            description = "Assign a specific role to a user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Role assigned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DetailMessageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request: invalid or duplicate role",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<Map<String, Object>> assignRole(
            @PathVariable UUID userId,
            @Valid @RequestBody AssignRole assignRole) {

        Map<String, Object> response = new HashMap<>();
        try {
            userService.assignRole(assignRole, userId);
            response.put("detail", "Role assigned successfully");
            return ResponseEntity.ok(response);
        } catch (BadRequestException e) {
            response.putAll(e.getErrorMessages());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (NotFoundException e) {
            response.putAll(e.getErrorMessages());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("detail", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("{staffId}/exists")
    @Operation(
            summary = "Check if staff exists",
            description = "Checks if staff exists in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "If true staff exists, otherwise not found",
                    content = @Content(
                            schema = @Schema(
                                    example = "true"
                            )
                    )
            )
    })
    public boolean staffExists(@PathVariable UUID staffId) {
        return userService.staffExists(staffId);
    }


    @GetMapping("{staffId}/zone-id")
    @PreAuthorize("hasAuthority('ROLE_INTERNAL')")
    public UUID getZoneId(@PathVariable UUID staffId) {
        return userService.getZoneId(staffId);
    }

}
