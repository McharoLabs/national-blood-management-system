package com.nbts.management.donor_service.service.impl;

import com.nbts.management.donor_service.dto.CreateDonorDTO;
import com.nbts.management.donor_service.dto.DonorResponseDTO;
import com.nbts.management.donor_service.entity.Donor;
import com.nbts.management.donor_service.exception.BadRequestException;
import com.nbts.management.donor_service.exception.ConflictException;
import com.nbts.management.donor_service.mapper.DonorMapper;
import com.nbts.management.donor_service.repository.DonorRepository;
import com.nbts.management.donor_service.service.DonorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DonorServiceImpl implements DonorService {
    private static final Logger logger = LoggerFactory.getLogger(DonorServiceImpl.class);
    private final DonorRepository donorRepository;

    public DonorServiceImpl(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    @Override
    public DonorResponseDTO createDonor(CreateDonorDTO createDonorDTO) throws BadRequestException, ConflictException {
        Map<String, String> errors = new HashMap<>();

        Optional<Donor> donorByFullName = donorRepository.findByFullNameIgnoreCase(createDonorDTO.getFullName());
        Optional<Donor> donorByPhoneNumber = donorRepository.findByPhoneNumber(createDonorDTO.getPhoneNumber());

        if (donorByFullName.isPresent()) {
            errors.put("fullName", "Donor with this name already exists");
        }

        if (donorByPhoneNumber.isPresent()) {
            errors.put("phoneNumber", "Donor with this phone number already exists");
        }

        if (!errors.isEmpty()) {
            throw new ConflictException(errors);
        }

        Donor donor = DonorMapper.toEntity(createDonorDTO);

        try {
            Donor insertedData = donorRepository.save(donor);

            return DonorMapper.toResponseDTO(insertedData);
        } catch (RuntimeException e) {
            logger.error("An unexpected error occurred while creating donor.", e);
            throw e;
        }
    }
}
