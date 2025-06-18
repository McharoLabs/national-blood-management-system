package com.nbts.management.donor_service.service;

import com.nbts.management.donor_service.dto.CreateDonorDTO;
import com.nbts.management.donor_service.dto.DonorResponseDTO;
import com.nbts.management.donor_service.dto.UpdateDonorDTO;
import com.nbts.management.donor_service.enums.Gender;
import com.nbts.management.donor_service.event.DonorAuthCreatedEvent;
import com.nbts.management.donor_service.exception.BadRequestException;
import com.nbts.management.donor_service.exception.ConflictException;
import com.nbts.management.donor_service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface DonorService {
    UUID createDonor(CreateDonorDTO createDonorDTO) throws BadRequestException, ConflictException;
    void updateDonor(UpdateDonorDTO updateDonorDTO) throws NotFoundException, BadRequestException;
    Page<DonorResponseDTO> getAllDonors(Pageable pageable, String fullName, Gender gender);
    boolean donorExists(UUID donorId);
    DonorResponseDTO getDonor(UUID donorId) throws NotFoundException;
    void setDonorAuthSavedTrue(DonorAuthCreatedEvent authCreatedEvent);
    List<DonorResponseDTO> getAllByAuthSavedFalse();

}
