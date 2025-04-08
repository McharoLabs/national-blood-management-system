package com.nbts.management.donor_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DonationType {
    REGULAR_BLOOD("Regular blood donation"),
    BLOOD_PRODUCTS("Blood products donation");

    private final String description;

    public static DonationType fromString(String text) {
        for (DonationType type : DonationType.values()) {
            if (type.description.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "DonationType{" +
                "description='" + description + '\'' +
                '}';
    }
}
