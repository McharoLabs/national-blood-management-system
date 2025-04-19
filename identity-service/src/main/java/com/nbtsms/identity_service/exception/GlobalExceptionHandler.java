package com.nbtsms.identity_service.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Handle Validation Exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errorMessages = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errorMessages.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    // Handle Conflict Exceptions
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, String>> handleConflictException(ConflictException ex) {
        return new ResponseEntity<>(ex.getErrorMessages(), HttpStatus.CONFLICT);
    }

    // Handle Not Found Exceptions
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getErrorMessages(), HttpStatus.NOT_FOUND);
    }

    // Handle Invalid Token Exceptions (Expired or Invalid Token)
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, String>> handleInvalidTokenException(InvalidTokenException ex) {
        Map<String, String> errorMessages = new HashMap<>();
        errorMessages.put("detail", ex.getMessage());
        return new ResponseEntity<>(errorMessages, HttpStatus.FORBIDDEN);
    }

    // Handle Access Denied Exception
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, String> errorMessages = new HashMap<>();
        errorMessages.put("detail",  ex.getMessage());
        return new ResponseEntity<>(errorMessages, HttpStatus.FORBIDDEN);
    }

    // Handle Unauthorized Access (401 Unauthorized)
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        Map<String, String> error = Map.of("detail", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, String>> handleExpiredJwtException(ExpiredJwtException ex) {
        Map<String, String> error = Map.of("detail", "Your session has expired. Please log in again.");
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(ForbiddenAccessException.class)
    public ResponseEntity<Map<String, String>> handleForbiddenAccessException(ForbiddenAccessException ex) {
        Map<String, String> errorMessages = new HashMap<>();
        errorMessages.put("detail", ex.getMessage());
        return new ResponseEntity<>(errorMessages, HttpStatus.FORBIDDEN);
    }

    // Handle Generic Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex) {
        Map<String, String> errorMessages = new HashMap<>();
        errorMessages.put("detail", ex.getMessage());
        return new ResponseEntity<>(errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
