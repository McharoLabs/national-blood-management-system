package com.management.nationalblood.laboratoryservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    private final Map<String, String> errorMessages;

    public ConflictException(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}

