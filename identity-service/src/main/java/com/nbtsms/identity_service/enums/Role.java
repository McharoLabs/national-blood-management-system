package com.nbtsms.identity_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("User"),
    SUPER_ADMIN("Super admin"),
    ADMIN("Admin"),
    COUNSELOR("Counselor"),
    LAB_TECHNICIAN("Lab technician"),
    ORGANIZER("Organizer");

    private final String value;

}
