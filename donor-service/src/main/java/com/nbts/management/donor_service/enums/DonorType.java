package com.nbts.management.donor_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DonorType {
    EMERGENCY("Emergency donor"),
    FAMILY("Family donor");

    private final String description;

    public static DonorType fromString(String text) {
        for (DonorType type : DonorType.values()) {
            if (type.description.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "DonorType{" +
                "description='" + description + '\'' +
                '}';
    }
}
