package com.nbts.management.donor_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MaritalStatus {
    SINGLE("Single"),
    MARRIED("Married"),
    DIVORCED("Divorced"),
    WIDOWED("Widowed");

    private final String displayName;

    public static MaritalStatus fromString(String value) {
        for (MaritalStatus status : MaritalStatus.values()) {
            if (status.displayName.equalsIgnoreCase(value) || status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown marital status: " + value);
    }

    @Override
    public String toString() {
        return "MaritalStatus{" +
                "displayName='" + displayName + '\'' +
                '}';
    }
}
