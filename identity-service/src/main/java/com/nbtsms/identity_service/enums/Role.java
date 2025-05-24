package com.nbtsms.identity_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER,
    SUPER_USER,
    ADMIN,
    COUNSELOR,
    LAB_TECHNICIAN,
    ORGANIZER;
}
