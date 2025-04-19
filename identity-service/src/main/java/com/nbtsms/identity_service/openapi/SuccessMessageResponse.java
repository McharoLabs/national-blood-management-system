package com.nbtsms.identity_service.openapi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Generic success message response.")
public class SuccessMessageResponse {

    @Schema(
            description = "Success message.",
            example = "Region added successfully."
    )
    private String message;
}