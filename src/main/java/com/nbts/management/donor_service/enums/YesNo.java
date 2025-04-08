package com.nbts.management.donor_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YesNo {
    YES("Yes"),
    NO("No");

    private final String value;

    public static YesNo fromString(String value) {
        for (YesNo yesNo : YesNo.values()) {
            if (yesNo.value.equalsIgnoreCase(value)) {
                return yesNo;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + value);
    }

    @Override
    public String toString() {
        return "YesNo{" +
                "value='" + value + '\'' +
                '}';
    }
}
