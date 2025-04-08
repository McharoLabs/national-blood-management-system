package com.nbts.management.donor_service.enums;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MedicalReason {
    WEIGHT("Weight"),
    AGE("Age"),
    CBC("CBC"),
    BP("BP");

    private final String medicalReason;

    @Override
    public String toString() {
        return "MedicalReason{" +
                "medicalReason='" + medicalReason + '\'' +
                '}';
    }


}