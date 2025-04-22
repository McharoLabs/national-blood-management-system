package com.nbts.management.donor_service.dto;

import com.nbts.management.donor_service.enums.Gender;
import com.nbts.management.donor_service.enums.MaritalStatus;
import com.nbts.management.donor_service.enums.PolygamousMarriage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonorResponseDTO {
    private UUID id;
    private String fullName;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private PolygamousMarriage polygamousMarriage;
    private Integer numberOfSpouses;
    private LocalDate dateOfBirth;
    private Integer age;
    private String nationality;
    private String phoneNumber;
    private String region;
    private String district;
    private String ward;
    private String street;
    private String address;
    private String educationLevel;
    private String occupation;
    private LocalDateTime lastDonation;
    private LocalDateTime createdAt;
}
