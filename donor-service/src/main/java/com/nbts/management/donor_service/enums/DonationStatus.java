package com.nbts.management.donor_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DonationStatus {
    FIRST_TIME("First time"),
    REPEAT("Repeat"),
    REGULAR("Regular"),
    BLOOD_DONOR_CLUB("Blood donor club"),
    BLOOD_DONOR_ASSOCIATION("Blood donor association");

    private final String description;

    public static DonationStatus fromString(String text) {
        for (DonationStatus status : DonationStatus.values()) {
            if (status.description.equalsIgnoreCase(text)) {
                return status;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "DonationStatus{" +
                "description='" + description + '\'' +
                '}';
    }
}
