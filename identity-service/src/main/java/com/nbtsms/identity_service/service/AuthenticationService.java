package com.nbtsms.identity_service.service;


import com.nbtsms.identity_service.dto.DonorSignInRequestDTO;
import com.nbtsms.identity_service.dto.JwtAuthenticationResponseDTO;
import com.nbtsms.identity_service.dto.RefreshTokenRequest;
import com.nbtsms.identity_service.dto.SignInRequestDTO;
import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.NotFoundException;

public interface AuthenticationService {
    JwtAuthenticationResponseDTO signIn(SignInRequestDTO signInRequestDTO) throws NotFoundException, BadRequestException;
    JwtAuthenticationResponseDTO refreshToken (RefreshTokenRequest refreshTokenRequest);
    JwtAuthenticationResponseDTO donorSignIn(DonorSignInRequestDTO signInRequestDTO) throws NotFoundException, BadRequestException;
}
