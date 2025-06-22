package com.nbtsms.identity_service.controller;

import com.nbtsms.identity_service.dto.*;
import com.nbtsms.identity_service.service.impl.UserServiceImpl;
import com.nbtsms.identity_service.swagger.IdentityUserPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Tag(name = "Staffs", description = "Endpoints related to staffs")
@RestController
@RequestMapping("")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPER_USER')")
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
                            schema = @Schema(implementation = IdentityResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed or bad request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User already exists (conflict)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<IdentityResponseDTO<Map<String, Object>>> create(
            @Valid @RequestBody CreateUserDTO userDTO,
            HttpServletRequest request
    ) {

        UUID userId = userService.create(userDTO);
        IdentityResponseDTO<Map<String, Object>> response = IdentityResponseDTO.ok(Map.of("userId", userId), "New user created successfully", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
    @Operation(
            summary = "Update an existing user",
            description = "Allows admin or super admin to update an existing user’s profile information."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = IdentityResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed or bad request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<IdentityResponseDTO<Map<String, Object>>> update(
            @Valid @RequestBody UpdateUserDTO updateUserDTO,
            HttpServletRequest request
    ) {
        UUID updatedUserId = userService.update(updateUserDTO);
        IdentityResponseDTO<Map<String, Object>> response = IdentityResponseDTO.ok(
                Map.of("userId", updatedUserId),
                "User updated successfully",
                request.getRequestURI()
        );
        return ResponseEntity.ok(response);
    }


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_USER')")
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
                            schema = @Schema(implementation = IdentityUserPageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<IdentityResponseDTO<Page<UserDTO>>> getAllUsers(
            HttpServletRequest request,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<UserDTO> userPage =  userService.getUsers(name, pageable);
        IdentityResponseDTO<Page<UserDTO>> response = IdentityResponseDTO.ok(userPage, "Users fetched successfully", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{userId}/user")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_USER')")
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
                            schema = @Schema(implementation = IdentityUserPageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<IdentityResponseDTO<UserDTO>> getUser(
            HttpServletRequest request,
            @PathVariable UUID userId
    ) {
        IdentityResponseDTO<UserDTO> response = IdentityResponseDTO.ok(userService.getUser(userId), "User fetched successfully", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("admins")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_USER')")
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
                            schema = @Schema(implementation = IdentityUserPageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )

    })
    public ResponseEntity<IdentityResponseDTO<Page<UserDTO>>> getAllAdmin(
            HttpServletRequest request,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<UserDTO> allAdmin = userService.getAllAdmin(name, pageable);
        IdentityResponseDTO<Page<UserDTO>> response = IdentityResponseDTO.ok(allAdmin, "Admins fetched successfully", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PatchMapping("{userId}/roles")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_USER')")
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
                            schema = @Schema(implementation = IdentityResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request: invalid or duplicate role",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<IdentityResponseDTO<Map<String, Object>>> assignRole(
            @PathVariable UUID userId,
            @Valid @RequestBody AssignRole assignRole,
            HttpServletRequest request
    ) {
        userService.assignRole(assignRole, userId);
        IdentityResponseDTO<Map<String, Object>> response = IdentityResponseDTO.ok(null, "Role changed successfully", request.getRequestURI());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_USER')")
    @Operation(
            summary = "Search users",
            description = "Search users by name and email."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Users fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = IdentityUserPageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<IdentityResponseDTO<Page<UserDTO>>> searchUsers(
            HttpServletRequest request,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<UserDTO> users = userService.searchUsers(name, email, pageable);
        IdentityResponseDTO<Page<UserDTO>> response = IdentityResponseDTO.ok(users, "Users search result", request.getRequestURI());
        return ResponseEntity.ok(response);
    }


    @GetMapping("{staffId}/exists")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_SUPER_USER', 'ROLE_ADMIN', 'ROLE_COUNSELOR', 'ROLE_LAB_TECHNICIAN', 'ROLE_ORGANIZER')")
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

    @GetMapping("available-staffs")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_USER')")
    @Operation(
            summary = "Available Staffs",
            description = "Retrieve the list of all available staffs to assign to the center"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Users fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = IdentityUserPageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<IdentityResponseDTO<List<UserDTO>>> availableStaffs(
            HttpServletRequest request,
            @RequestParam(required = false) String name
    ) {
        List<UserDTO> userPage =  userService.availableStaffs(name);
        IdentityResponseDTO<List<UserDTO>> response = IdentityResponseDTO.ok(userPage, "Staffs fetched successfully", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("available-admin")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_USER')")
    @Operation(
            summary = "Available Admin",
            description = "Retrieve the list of all available admin to assign to the zone"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Users fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = IdentityUserPageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<IdentityResponseDTO<List<UserDTO>>> availableAdmin(
            HttpServletRequest request,
            @RequestParam(required = false) String name
    ) {
        List<UserDTO> userPage =  userService.availableAdmin(name);
        IdentityResponseDTO<List<UserDTO>> response = IdentityResponseDTO.ok(userPage, "Staffs fetched successfully", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("{staffId}/zone-id")
    @PreAuthorize("hasAuthority('ROLE_INTERNAL')")
    public UUID getZoneId(@PathVariable UUID staffId) {
        return userService.getZoneId(staffId);
    }

}
