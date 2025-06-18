package com.nbtsms.identity_service.service.impl;

import com.nbtsms.identity_service.dto.DonorSignInRequestDTO;
import com.nbtsms.identity_service.dto.JwtAuthenticationResponseDTO;
import com.nbtsms.identity_service.dto.RefreshTokenRequest;
import com.nbtsms.identity_service.dto.SignInRequestDTO;
import com.nbtsms.identity_service.entity.DonorAuth;
import com.nbtsms.identity_service.entity.User;
import com.nbtsms.identity_service.exception.BadRequestException;
import com.nbtsms.identity_service.exception.NotFoundException;
import com.nbtsms.identity_service.repository.DonorAuthRepository;
import com.nbtsms.identity_service.repository.UserRepository;
import com.nbtsms.identity_service.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final UserRepository userRepository;
    private final DonorAuthRepository donorAuthRepository;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserRepository userRepository, DonorAuthRepository donorAuthRepository, JwtServiceImpl jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.donorAuthRepository = donorAuthRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public JwtAuthenticationResponseDTO signIn(SignInRequestDTO signInRequestDTO) throws NotFoundException, BadRequestException {
        Map<String, String> errors = new HashMap<>();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequestDTO.getEmail(),
                signInRequestDTO.getPassword()
        ));

        Optional<User> user = userRepository.findByEmail(signInRequestDTO.getEmail());

        if (user.isPresent()) {
            UserDetails userDetails = user.get();
            String access = jwtService.generateToken(userDetails);
            String refresh = jwtService.generateRefresh(new HashMap<>(), userDetails);

            JwtAuthenticationResponseDTO responseDTO = new JwtAuthenticationResponseDTO();
            responseDTO.setAccess(access);
            responseDTO.setRefresh(refresh);

            return responseDTO;
        }

        errors.put("email", "Invalid credentials");
        errors.put("password", "Invalid credentials");
        throw new NotFoundException(errors);
    }

    @Override
    public JwtAuthenticationResponseDTO refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());

        Optional<User> user = userRepository.findByEmail(userEmail);

        if (user.isPresent() && jwtService.isTokeValid(refreshTokenRequest.getToken(), user.get())) {
            String access = jwtService.generateToken(user.get());

            JwtAuthenticationResponseDTO responseDTO = new JwtAuthenticationResponseDTO();
            responseDTO.setAccess(access);
            responseDTO.setRefresh(refreshTokenRequest.getToken());

            return responseDTO;
        }

        return null;
    }

    @Override
    public JwtAuthenticationResponseDTO donorSignIn(DonorSignInRequestDTO signInRequestDTO) throws NotFoundException, BadRequestException {
        Map<String, String> errors = new HashMap<>();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequestDTO.getPhoneNumber(),
                signInRequestDTO.getPassword()
        ));

        Optional<DonorAuth> user = donorAuthRepository.findByPhoneNumber(signInRequestDTO.getPhoneNumber());

        if (user.isPresent()) {
            UserDetails userDetails = user.get();
            String access = jwtService.generateDonorToken(userDetails);
            String refresh = jwtService.generateDonorRefresh(new HashMap<>(), userDetails);

            JwtAuthenticationResponseDTO responseDTO = new JwtAuthenticationResponseDTO();
            responseDTO.setAccess(access);
            responseDTO.setRefresh(refresh);

            return responseDTO;
        }

        errors.put("phoneNumber", "Invalid credentials");
        errors.put("password", "Invalid credentials");
        throw new NotFoundException(errors);
    }
}
