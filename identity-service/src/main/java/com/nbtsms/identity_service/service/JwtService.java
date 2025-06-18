package com.nbtsms.identity_service.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    String generateDonorToken(UserDetails userDetails);
    String extractUsername(String token);
    boolean isTokeValid(String token, UserDetails userDetails);

    String generateRefresh(HashMap<Object, Object> extraClaims, UserDetails userDetails);
    String generateDonorRefresh(HashMap<Object, Object> extraClaims, UserDetails userDetails);
}
