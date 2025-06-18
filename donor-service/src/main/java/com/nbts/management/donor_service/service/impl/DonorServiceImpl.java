package com.nbts.management.donor_service.service.impl;

import com.nbts.management.donor_service.constants.App;
import com.nbts.management.donor_service.constants.KafkaTopics;
import com.nbts.management.donor_service.dto.CreateDonorDTO;
import com.nbts.management.donor_service.dto.DonorResponseDTO;
import com.nbts.management.donor_service.dto.UpdateDonorDTO;
import com.nbts.management.donor_service.entity.Donor;
import com.nbts.management.donor_service.enums.Gender;
import com.nbts.management.donor_service.event.DonorAuthCreatedEvent;
import com.nbts.management.donor_service.event.DonorNotificationEvent;
import com.nbts.management.donor_service.exception.BadRequestException;
import com.nbts.management.donor_service.exception.ConflictException;
import com.nbts.management.donor_service.exception.NotFoundException;
import com.nbts.management.donor_service.mapper.DonorMapper;
import com.nbts.management.donor_service.repository.DonorRepository;
import com.nbts.management.donor_service.service.DonorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DonorServiceImpl implements DonorService {
    private static final Logger logger = LoggerFactory.getLogger(DonorServiceImpl.class);
    private final DonorRepository donorRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DonorServiceImpl(DonorRepository donorRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.donorRepository = donorRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public UUID createDonor(CreateDonorDTO createDonorDTO) throws BadRequestException, ConflictException {
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

            kafkaTemplate.send(
                    KafkaTopics.DONOR_NOTIFICATION,
                    new DonorNotificationEvent(
                            insertedData.getPhoneNumber(),
                            String.format(
                                    "Umefanikiwa kufungua akaunti Damu Salama, Username yako ni: %s Password ni: 12345. Tembelea %s kutazama taarifa zako",
                                    insertedData.getPhoneNumber(),
                                    App.DONOR_APP_URL
                            ),
                            App.DONOR_APP_URL
                    )
            );

            return insertedData.getId();
        } catch (RuntimeException e) {
            logger.error("An unexpected error occurred while creating donor.", e);
            throw e;
        }
    }

    @Override
    public void updateDonor(UpdateDonorDTO updateDonorDTO) throws NotFoundException, BadRequestException {
        logger.info("Attempting to update donor with ID: {}", updateDonorDTO.getId());

        Donor existingDonor = donorRepository.findById(updateDonorDTO.getId())
                .orElseThrow(() -> {
                    logger.warn("Donor with ID {} not found", updateDonorDTO.getId());
                    return new NotFoundException(Map.of("detail", "Donor not found"));
                });

        Map<String, String> errors = new HashMap<>();

        donorRepository.findByFullNameIgnoreCase(updateDonorDTO.getFullName()).ifPresent(donor -> {
            if (!donor.getId().equals(existingDonor.getId())) {
                errors.put("fullName", "Donor with this name already exists");
            }
        });

        donorRepository.findByPhoneNumber(updateDonorDTO.getPhoneNumber()).ifPresent(donor -> {
            if (!donor.getId().equals(existingDonor.getId())) {
                errors.put("phoneNumber", "Donor with this phone number already exists");
            }
        });

        if (!errors.isEmpty()) {
            logger.warn("Validation errors while updating donor: {}", errors);
            throw new BadRequestException(errors);
        }

        DonorMapper.updateEntity(existingDonor, updateDonorDTO);
        donorRepository.save(existingDonor);

        logger.info("Successfully updated donor with ID: {}", existingDonor.getId());
    }


    @Override
    public Page<DonorResponseDTO> getAllDonors(Pageable pageable, String fullName, Gender gender) {
        return donorRepository.searchDonors(gender, fullName, pageable)
                .map(DonorMapper::toResponseDTO);
    }

    @Override
    public boolean donorExists(UUID donorId) {
        return donorRepository.findById(donorId).isPresent();
    }

    @Override
    public DonorResponseDTO getDonor(UUID donorId) throws NotFoundException {
        return DonorMapper.toResponseDTO(
                donorRepository
                        .findById(donorId)
                        .orElseThrow(() -> new NotFoundException(Map.of("detail", "Donor not found")))
        );
    }

    @Override
    public void setDonorAuthSavedTrue(DonorAuthCreatedEvent authCreatedEvent) {
        donorRepository.findById(authCreatedEvent.getDonorId())
                .ifPresent(donor -> {
                    donor.setAuthSaved(true);
                    donorRepository.save(donor);
                });
    }

    @Override
    public List<DonorResponseDTO> getAllByAuthSavedFalse() {
        return donorRepository.findAllByAuthSavedFalse()
                .stream()
                .map(DonorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
