package com.nbtsms.identity_service.openapi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DetailMessageResponse {

    @Schema(description = "Detailed error message", example = "Internal server error occurred.")
    private String detail;
}
