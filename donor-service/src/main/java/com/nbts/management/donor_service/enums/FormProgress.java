package com.nbts.management.donor_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FormProgress {
    NOT_STARTED("Not Started"),
    PRELIMINARY_COMPLETED("Preliminary Completed"),
    PHYSICAL_EXAM_COMPLETED("Physical Exam Completed"),
    HAEMATOLOGICAL_TESTS_COMPLETED("Haematological Tests Completed"),
    BLOOD_PRESSURE_PULSE_COMPLETED("Blood Pressure & Pulse Completed"),
    FINAL_EVALUATION_COMPLETED("Final Evaluation Completed"),
    COMPLETED("Completed");

    private final String description;

    @Override
    public String toString() {
        return description;
    }
}