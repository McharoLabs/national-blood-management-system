package com.management.nationalblood.meeting.dto;

import com.management.nationalblood.meeting.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonorResponseDTO {
    private UUID id;
    private String fullName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Integer age;
    private String nationality;
    private String phoneNumber;
    private String region;
    private String district;
    private String ward;
    private String street;
}
