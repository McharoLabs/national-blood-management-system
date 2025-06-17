package com.management.nationalblood.meeting.dto;

import com.management.nationalblood.meeting.enums.BloodProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BloodCollectionDataDTO {
    @NotNull(message = "Questionnaire Id is mandatory")
    private UUID questionnaireId;

    @NotNull(message = "Time needle inserted is required.")
    private LocalTime timeNeedleInserted;

    @NotNull(message = "Time needle removed is required.")
    private LocalTime timeNeedleRemoved;

    @NotNull(message = "Venipuncture success status is required.")
    private Boolean venipunctureSuccessful;

    @NotNull(message = "Blood collection unsuccessful status is required.")
    private Boolean bloodCollectionUnsuccessful;

    @NotBlank(message = "Small amount collected is required.")
    @Size(max = 255, message = "Small amount collected should not exceed 255 characters.")
    private String smallAmountCollected;

    @NotBlank(message = "Quantity of blood collected is required.")
    @Size(max = 255, message = "Quantity of blood collected should not exceed 255 characters.")
    private String quantityOfBloodCollected;

    @NotBlank(message = "Scale used is required.")
    @Size(max = 255, message = "Scale used should not exceed 255 characters.")
    private String scaleUsed;

    @NotBlank(message = "Apheresis machine used is required.")
    @Size(max = 255, message = "Apheresis machine used should not exceed 255 characters.")
    private String apheresisMachineUsed;

    @NotBlank(message = "Blood bag type is required.")
    @Size(max = 255, message = "Blood bag type should not exceed 255 characters.")
    private String bloodBagType;

    @NotBlank(message = "Blood bag lot number is required.")
    @Size(max = 255, message = "Blood bag lot number should not exceed 255 characters.")
    private String bloodBagLotNumber;

    @NotNull(message = "Blood bag expiry date is required.")
    private LocalDate bloodBagExpiryDate;

    @NotNull(message = "Blood product type is required.")
    private BloodProductType bloodProductType;
}
