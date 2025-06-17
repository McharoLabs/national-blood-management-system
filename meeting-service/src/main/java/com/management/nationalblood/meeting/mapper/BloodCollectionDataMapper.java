package com.management.nationalblood.meeting.mapper;


import com.management.nationalblood.meeting.dto.BloodCollectionDataDTO;
import com.management.nationalblood.meeting.dto.BloodCollectionDataResponseDTO;
import com.management.nationalblood.meeting.entity.BloodCollectionData;

public class BloodCollectionDataMapper {
    public static BloodCollectionData toEntity(BloodCollectionDataDTO dto) {
        BloodCollectionData entity = new BloodCollectionData();
        entity.setTimeNeedleInserted(dto.getTimeNeedleInserted());
        entity.setTimeNeedleRemoved(dto.getTimeNeedleRemoved());
        entity.setVenipunctureSuccessful(dto.getVenipunctureSuccessful());
        entity.setBloodCollectionUnsuccessful(dto.getBloodCollectionUnsuccessful());
        entity.setSmallAmountCollected(dto.getSmallAmountCollected());
        entity.setQuantityOfBloodCollected(dto.getQuantityOfBloodCollected());
        entity.setScaleUsed(dto.getScaleUsed());
        entity.setApheresisMachineUsed(dto.getApheresisMachineUsed());
        entity.setBloodBagType(dto.getBloodBagType());
        entity.setBloodBagLotNumber(dto.getBloodBagLotNumber());
        entity.setBloodBagExpiryDate(dto.getBloodBagExpiryDate());
        entity.setBloodProductType(dto.getBloodProductType());
        return entity;
    }

    public static BloodCollectionDataResponseDTO toResponse(BloodCollectionData data) {
        return BloodCollectionDataResponseDTO.builder()
                .id(data.getId())
                .bloodBagType(data.getBloodBagType())
                .bloodCollectionUnsuccessful(data.isBloodCollectionUnsuccessful())
                .quantityOfBloodCollected(data.getQuantityOfBloodCollected())
                .apheresisMachineUsed(data.getApheresisMachineUsed())
                .bloodProductType(data.getBloodProductType())
                .bloodBagExpiryDate(data.getBloodBagExpiryDate())
                .bloodBagLotNumber(data.getBloodBagLotNumber())
                .smallAmountCollected(data.getSmallAmountCollected())
                .timeNeedleInserted(data.getTimeNeedleInserted())
                .timeNeedleRemoved(data.getTimeNeedleRemoved())
                .venipunctureSuccessful(data.isVenipunctureSuccessful())
                .scaleUsed(data.getScaleUsed())
                .build();
    }
}
