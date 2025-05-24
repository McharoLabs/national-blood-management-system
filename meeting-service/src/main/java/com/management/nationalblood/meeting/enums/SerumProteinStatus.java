package com.management.nationalblood.meeting.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SerumProteinStatus {
    NORMAL("Normal"),
    ABNORMAL("Abnormal");

    private final String status;

    public static SerumProteinStatus getFromString(String value) {
        for (SerumProteinStatus serumProteinStatus : SerumProteinStatus.values()) {
            if (serumProteinStatus.status.equalsIgnoreCase(value)) {
                return serumProteinStatus;
            }

            throw new IllegalArgumentException("Unexpected value " + value);
        }
        return null;
    }

    @Override
    public String toString() {
        return "SerumProteinStatus{" +
                "status='" + status + '\'' +
                '}';
    }
}
