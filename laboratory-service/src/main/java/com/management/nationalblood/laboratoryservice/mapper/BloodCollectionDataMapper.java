package com.management.nationalblood.laboratoryservice.mapper;

import com.management.nationalblood.laboratoryservice.dto.BloodCollectionDataResponseDTO;
import com.management.nationalblood.laboratoryservice.entity.BloodCollectionData;

public class BloodCollectionDataMapper {
    public static BloodCollectionDataResponseDTO toResponse(BloodCollectionData entity) {
        return BloodCollectionDataResponseDTO.builder()
                .timeNeedleInserted(entity.getTimeNeedleInserted())
                .timeNeedleRemoved(entity.getTimeNeedleRemoved())
                .venipunctureSuccessful(entity.isVenipunctureSuccessful())
                .bloodCollectionUnsuccessful(entity.isBloodCollectionUnsuccessful())
                .smallAmountCollected(entity.getSmallAmountCollected())
                .quantityOfBloodCollected(entity.getQuantityOfBloodCollected())
                .scaleUsed(entity.getScaleUsed())
                .apheresisMachineUsed(entity.getApheresisMachineUsed())
                .bloodBagType(entity.getBloodBagType())
                .bloodBagLotNumber(entity.getBloodBagLotNumber())
                .bloodBagExpiryDate(entity.getBloodBagExpiryDate())
                .bloodProductType(entity.getBloodProductType())
                .build();
    }

    public static BloodCollectionData toEntity(BloodCollectionDataResponseDTO dto) {
        if (dto == null) return null;

        return BloodCollectionData.builder()
                .timeNeedleInserted(dto.getTimeNeedleInserted())
                .timeNeedleRemoved(dto.getTimeNeedleRemoved())
                .venipunctureSuccessful(dto.isVenipunctureSuccessful())
                .bloodCollectionUnsuccessful(dto.isBloodCollectionUnsuccessful())
                .smallAmountCollected(dto.getSmallAmountCollected())
                .quantityOfBloodCollected(dto.getQuantityOfBloodCollected())
                .scaleUsed(dto.getScaleUsed())
                .apheresisMachineUsed(dto.getApheresisMachineUsed())
                .bloodBagType(dto.getBloodBagType())
                .bloodBagLotNumber(dto.getBloodBagLotNumber())
                .bloodBagExpiryDate(dto.getBloodBagExpiryDate())
                .bloodProductType(dto.getBloodProductType())
                .build();
    }
}
