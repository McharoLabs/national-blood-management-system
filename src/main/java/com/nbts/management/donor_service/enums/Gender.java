package com.nbts.management.donor_service.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String gender;

    public static Gender fromString(String gender) {
        for (Gender g : Gender.values()) {
            if (g.getGender().equalsIgnoreCase(gender)) {
                return g;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + gender);
    }

    @Override
    public String toString() {
        return "Gender{" +
                "gender='" + gender + '\'' +
                '}';
    }
}
