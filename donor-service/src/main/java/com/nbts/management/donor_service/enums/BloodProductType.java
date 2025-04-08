package com.nbts.management.donor_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BloodProductType {
    WHOLE_BLOOD("Whole blood"),
    RED_BLOOD_CELLS("Red blood cells"),
    PLASMA("Plasma"),
    PLATELETS("Platelets");

    private final String bloodProductType;

    @Override
    public String toString() {
        return "BloodProductType{" +
                "bloodProductType='" + bloodProductType + '\'' +
                '}';
    }
}
