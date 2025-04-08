package com.nbts.management.donor_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DonorStatus {
    ALLOWED_TO_DONATE("Allowed to donate"),
    TEMPORARILY_DEFERRED("Temporarily deferred"),
    PERMANENTLY_DEFERRED("Permanently deferred");

    private final String value;

    public static DonorStatus fromString(String value) {
        for (DonorStatus status : DonorStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "DonorStatus{" +
                "value='" + value + '\'' +
                '}';
    }
}
