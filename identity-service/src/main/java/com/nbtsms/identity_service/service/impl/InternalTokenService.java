package com.nbtsms.identity_service.service.impl;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class InternalTokenService {
    @Value("${jwt.private.key.path}")
    private Resource privateKeyResource;

    private PrivateKey privateKey;
    private String currentToken;
    private Instant expiration;
    private final ReentrantLock lock = new ReentrantLock();

    @PostConstruct
    public void init() {
        this.privateKey = loadPrivateKey();
    }

    public String getToken() {
        lock.lock();
        try {
            if (currentToken == null || Instant.now().isAfter(expiration)) {
                generateToken();
            }
            return currentToken;
        } finally {
            lock.unlock();
        }
    }

    private void generateToken() {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(3600);

        this.currentToken = Jwts.builder()
                .subject("internal-service")
                .claim("roles", List.of("INTERNAL"))
                .claim("user", Map.of("id", "internal-system"))
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(privateKey, Jwts.SIG.RS512)
                .compact();

        this.expiration = expiry.minusSeconds(30);
    }

    private PrivateKey loadPrivateKey() {
        try (InputStream inputStream = privateKeyResource.getInputStream()) {
            byte[] keyBytes = inputStream.readAllBytes();
            String privateKeyPEM = new String(keyBytes)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load private key", e);
        }
    }
}
