package com.nbtsms.identity_service.controller;

import com.nbtsms.identity_service.dto.*;
import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.NotFoundException;
import com.nbtsms.identity_service.service.impl.AuthenticationServiceImpl;
import com.nbtsms.identity_service.swagger.IdentityAuthResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Endpoints related to authentication")
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    public AuthenticationController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("sign-in")
    @Operation(
            summary = "Authenticate user and return JWT token",
            description = "Validates user credentials and returns a JWT token if authentication is successful."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully authenticated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = IdentityAuthResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found or invalid credentials",
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
    public ResponseEntity<IdentityResponseDTO<JwtAuthenticationResponseDTO>> signIn(
            @Valid @RequestBody SignInRequestDTO signInRequestDTO,
            HttpServletRequest request
    ) {
        JwtAuthenticationResponseDTO tokens = authenticationService.signIn(signInRequestDTO);
        IdentityResponseDTO<JwtAuthenticationResponseDTO> response =
                IdentityResponseDTO.ok(tokens, "User authenticated successfully", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("donor/sign-in")
    @Operation(
            summary = "Authenticate donor and return JWT token",
            description = "Validates donor credentials and returns a JWT token if authentication is successful."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully authenticated donor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = JwtAuthenticationResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Donor not found or invalid credentials",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request due to invalid input or unverified donor",
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
    public ResponseEntity<IdentityResponseDTO<JwtAuthenticationResponseDTO>> donorSignIn(
            @Valid @RequestBody DonorSignInRequestDTO donorSignInRequestDTO,
            HttpServletRequest request
    ) throws NotFoundException, BadRequestException {
        JwtAuthenticationResponseDTO tokens = authenticationService.donorSignIn(donorSignInRequestDTO);
        IdentityResponseDTO<JwtAuthenticationResponseDTO> response =
                IdentityResponseDTO.ok(tokens, "Donor authenticated successfully", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("refresh")
    @Operation(
            summary = "Refresh JWT token",
            description = "Takes a valid refresh token and returns a new JWT access token."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Token refreshed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = IdentityAuthResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Refresh token not found or invalid",
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
    public ResponseEntity<IdentityResponseDTO<JwtAuthenticationResponseDTO>> refresh(
            @Valid @RequestBody RefreshTokenRequest refreshTokenRequest,
            HttpServletRequest request
    ) {
        JwtAuthenticationResponseDTO tokens = authenticationService.refreshToken(refreshTokenRequest);
        IdentityResponseDTO<JwtAuthenticationResponseDTO> response = IdentityResponseDTO.ok(tokens, "Refresh token created successfully", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
