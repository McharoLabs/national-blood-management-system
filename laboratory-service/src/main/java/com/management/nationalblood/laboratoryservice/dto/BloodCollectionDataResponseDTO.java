package com.management.nationalblood.laboratoryservice.dto;

import com.management.nationalblood.laboratoryservice.enums.BloodProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BloodCollectionDataResponseDTO {

    private UUID id;

    private LocalTime timeNeedleInserted;

    private LocalTime timeNeedleRemoved;

    private boolean venipunctureSuccessful;

    private boolean bloodCollectionUnsuccessful;

    private String smallAmountCollected;

    private String quantityOfBloodCollected;

    private String scaleUsed;

    private String apheresisMachineUsed;

    private String bloodBagType;

    private String bloodBagLotNumber;

    private LocalDate bloodBagExpiryDate;

    private BloodProductType bloodProductType;
}
