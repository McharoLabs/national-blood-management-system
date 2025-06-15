package com.management.nationalblood.meeting.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FormProgress {
    NOT_STARTED,
    PRELIMINARY_COMPLETED,
    PHYSICAL_EXAM_COMPLETED,
    HAEMATOLOGICAL_TESTS_COMPLETED,
    BLOOD_PRESSURE_PULSE_COMPLETED,
    FINAL_EVALUATION_COMPLETED,
    BLOOD_COLLECTED,
    COMPLETED;
}