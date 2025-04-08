package com.nbts.management.donor_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FaintingLevel {
    NONE("None"),
    MINOR("Minor"),
    MODERATE("Moderate"),
    SEVERE("Severe");

    private final String faintingLeve;

    @Override
    public String toString() {
        return "FaintingLevel{" +
                "faintingLeve='" + faintingLeve + '\'' +
                '}';
    }
}
