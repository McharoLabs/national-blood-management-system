package com.nbts.management.donor_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialReason {
    RISK_BEHAVIOR("Risk behaviour"),
    TRAVEL_HISTORY("Travel history"),
    OTHER("Other");

    private final String socialReason;

    @Override
    public String toString() {
        return "SocialReason{" +
                "socialReason='" + socialReason + '\'' +
                '}';
    }
}
