package com.nbtsms.identity_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentityResponseDTO<T> {
    private Instant timestamp;
    private int status;
    private boolean success;
    private String message;
    private T data;
    private String path;

    public static <T> IdentityResponseDTO<T> ok(T data, String message, String path) {
        return new IdentityResponseDTO<>(Instant.now(), 200, true, message, data, path);
    }

    public static <T> IdentityResponseDTO<T> error(int status, String message, String path) {
        return new IdentityResponseDTO<>(Instant.now(), status, false, message, null, path);
    }
}
