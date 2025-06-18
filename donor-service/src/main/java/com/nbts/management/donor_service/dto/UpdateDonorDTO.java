package com.nbts.management.donor_service.dto;

import com.nbts.management.donor_service.enums.Gender;
import com.nbts.management.donor_service.enums.MaritalStatus;
import com.nbts.management.donor_service.enums.PolygamousMarriage;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDonorDTO {
    @NotNull(message = "Donor ID is mandatory")
    private UUID id;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Marital status is required")
    private MaritalStatus maritalStatus;

    @NotNull(message = "Polygamous marriage information is required")
    private PolygamousMarriage polygamousMarriage;

    private Integer numberOfSpouses;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Minimum age is 18")
    @Max(value = 100, message = "Maximum age is 100")
    private Integer age;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" +
            "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" +
            "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$",
            message = "Invalid phone number format. Please use a valid phone number format.")
    private String phoneNumber;

    @NotBlank(message = "Region is required")
    private String region;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "Ward is required")
    private String ward;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "Address is required")
    private String address;

    private String educationLevel;

    @NotBlank(message = "Occupation is required")
    private String occupation;
}
