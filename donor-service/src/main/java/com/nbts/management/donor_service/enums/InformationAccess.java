package com.nbts.management.donor_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InformationAccess {
    MEDIA("Media"),
    SMS("Using SMS"),
    PHONE_CALLS("Phone calls"),
    MEETINGS("Meetings"),
    MOBILE_NETWORKS("Mobile networks");

    private final String description;

    public static InformationAccess fromString(String text) {
        for (InformationAccess access : InformationAccess.values()) {
            if (access.description.equalsIgnoreCase(text)) {
                return access;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "InformationAccess{" +
                "description='" + description + '\'' +
                '}';
    }
}
