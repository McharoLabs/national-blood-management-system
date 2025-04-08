package com.nbts.management.donor_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PolygamousMarriage {
    YES("Yes"),
    NO("No");

    private final String label;

    public static PolygamousMarriage fromString(String value) {
        for (PolygamousMarriage pm : PolygamousMarriage.values()) {
            if (pm.label.equalsIgnoreCase(value) || pm.name().equalsIgnoreCase(value)) {
                return pm;
            }
        }
        throw new IllegalArgumentException("Unknown value for PolygamousMarriage: " + value);
    }

    @Override
    public String toString() {
        return "PolygamousMarriage{" +
                "label='" + label + '\'' +
                '}';
    }
}
