package com.nbtsms.identity_service.openapi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
@Schema(description = "Field-level error response containing key-value pairs of field names and error messages.")
public class FieldErrorResponse {

    @Schema(
            description = "A map of field names to their respective error messages.",
            example = "{ \"name\": \"Region with this name already exists.\", \"zone\": \"Zone not found.\" }"
    )
    private Map<String, String> errors;
}
