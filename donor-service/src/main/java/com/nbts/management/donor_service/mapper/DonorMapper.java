package com.nbts.management.donor_service.mapper;

import com.nbts.management.donor_service.dto.CreateDonorDTO;
import com.nbts.management.donor_service.dto.DonorResponseDTO;
import com.nbts.management.donor_service.dto.UpdateDonorDTO;
import com.nbts.management.donor_service.entity.Donor;
import com.nbts.management.donor_service.enums.PolygamousMarriage;
import com.nbts.management.donor_service.exception.BadRequestException;

import java.util.Map;

public class DonorMapper {

    public static void updateEntity(Donor donor, UpdateDonorDTO dto) {
        if (PolygamousMarriage.YES.equals(dto.getPolygamousMarriage())) {
            if (dto.getNumberOfSpouses() == null || dto.getNumberOfSpouses() <= 1) {
                throw new BadRequestException(Map.of(
                        "detail", "Number of spouses must be greater than one for polygamous marriage"
                ));
            }
        }

        donor.setFullName(dto.getFullName());
        donor.setGender(dto.getGender());
        donor.setMaritalStatus(dto.getMaritalStatus());
        donor.setPolygamousMarriage(dto.getPolygamousMarriage());
        donor.setNumberOfSpouses(dto.getNumberOfSpouses());
        donor.setDateOfBirth(dto.getDateOfBirth());
        donor.setAge(dto.getAge());
        donor.setNationality(dto.getNationality());
        donor.setPhoneNumber(dto.getPhoneNumber());
        donor.setRegion(dto.getRegion());
        donor.setDistrict(dto.getDistrict());
        donor.setWard(dto.getWard());
        donor.setStreet(dto.getStreet());
        donor.setAddress(dto.getAddress());
        donor.setEducationLevel(dto.getEducationLevel());
        donor.setOccupation(dto.getOccupation());
    }

    public static Donor toEntity(CreateDonorDTO dto) {
        if (PolygamousMarriage.YES.equals(dto.getPolygamousMarriage())) {
            if (dto.getNumberOfSpouses() == null || dto.getNumberOfSpouses() <= 1) {
                throw new BadRequestException(Map.of(
                        "detail", "Number of spouses must be greater than one for polygamous marriage"
                ));
            }
        }


        Donor donor = new Donor();
        donor.setFullName(dto.getFullName());
        donor.setMaritalStatus(dto.getMaritalStatus());
        donor.setPolygamousMarriage(dto.getPolygamousMarriage());
        donor.setNumberOfSpouses(dto.getNumberOfSpouses());
        donor.setDateOfBirth(dto.getDateOfBirth());
        donor.setAge(dto.getAge());
        donor.setNationality(dto.getNationality());
        donor.setPhoneNumber(dto.getPhoneNumber());
        donor.setRegion(dto.getRegion());
        donor.setDistrict(dto.getDistrict());
        donor.setWard(dto.getWard());
        donor.setStreet(dto.getStreet());
        donor.setAddress(dto.getAddress());
        donor.setEducationLevel(dto.getEducationLevel());
        donor.setOccupation(dto.getOccupation());
        donor.setGender(dto.getGender());

        return donor;
    }


    public static DonorResponseDTO toResponseDTO(Donor donor) {
        return DonorResponseDTO.builder()
                .id(donor.getId())
                .fullName(donor.getFullName())
                .gender(donor.getGender())
                .maritalStatus(donor.getMaritalStatus())
                .polygamousMarriage(donor.getPolygamousMarriage())
                .numberOfSpouses(donor.getNumberOfSpouses())
                .dateOfBirth(donor.getDateOfBirth())
                .age(donor.getAge())
                .nationality(donor.getNationality())
                .phoneNumber(donor.getPhoneNumber())
                .region(donor.getRegion())
                .district(donor.getDistrict())
                .ward(donor.getWard())
                .street(donor.getStreet())
                .address(donor.getAddress())
                .educationLevel(donor.getEducationLevel())
                .occupation(donor.getOccupation())
                .lastDonation(donor.getLastDonation())
                .build();
    }
}
