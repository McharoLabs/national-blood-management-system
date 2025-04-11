package com.nbtsms.zone_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private final Map<String, String> errorMessages;

    public BadRequestException(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
