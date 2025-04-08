package com.nbts.management.donor_service.mapper;

import com.nbts.management.donor_service.dto.CreateDonorDTO;
import com.nbts.management.donor_service.dto.DonorResponseDTO;
import com.nbts.management.donor_service.entity.Donor;

public class DonorMapper {

    public static Donor toEntity(CreateDonorDTO dto) {
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
