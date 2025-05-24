package com.nbtsms.identity_service.swagger;

import com.nbtsms.identity_service.dto.IdentityResponseDTO;
import com.nbtsms.identity_service.dto.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Schema(name = "UserResponseWrapper", description = "Wrapped paginated list of users")
public class IdentityUserPageResponse extends IdentityResponseDTO<Page<UserDTO>> {
}
