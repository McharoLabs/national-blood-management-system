package com.management.nationalblood.meeting.mapper;

import com.management.nationalblood.meeting.dto.CreateAssessmentDTO;
import com.management.nationalblood.meeting.dto.DonorResponseDTO;
import com.management.nationalblood.meeting.entity.Donor;

public class DonorMapper {
    public static Donor toEntity(CreateAssessmentDTO dto) {
        Donor donor = new Donor();

        donor.setFullName(dto.getFullName());

        donor.setDateOfBirth(dto.getDateOfBirth());
        donor.setAge(dto.getAge());
        donor.setNationality(dto.getNationality());
        donor.setPhoneNumber(dto.getPhoneNumber());
        donor.setRegion(dto.getRegion());
        donor.setDistrict(dto.getDistrict());
        donor.setWard(dto.getWard());
        donor.setStreet(dto.getStreet());
        donor.setGender(dto.getGender());
        donor.setDonorId(dto.getDonorId());

        return donor;
    }

    public static DonorResponseDTO toResponseDTO(Donor donor) {
        return DonorResponseDTO.builder()
                .id(donor.getId())
                .fullName(donor.getFullName())
                .gender(donor.getGender())
                .dateOfBirth(donor.getDateOfBirth())
                .age(donor.getAge())
                .nationality(donor.getNationality())
                .phoneNumber(donor.getPhoneNumber())
                .region(donor.getRegion())
                .district(donor.getDistrict())
                .ward(donor.getWard())
                .street(donor.getStreet())
                .build();
    }
}
