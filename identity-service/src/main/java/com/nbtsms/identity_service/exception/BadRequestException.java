package com.nbtsms.identity_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BadRequestException extends RuntimeException{
    private final Map<String, String> errorMessages;

    public BadRequestException(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}


