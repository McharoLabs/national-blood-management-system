package com.nbts.management.donor_service.service;

import com.nbts.management.donor_service.dto.CreateDonorDTO;
import com.nbts.management.donor_service.dto.DonorResponseDTO;
import com.nbts.management.donor_service.entity.Donor;
import com.nbts.management.donor_service.exception.BadRequestException;
import com.nbts.management.donor_service.exception.ConflictException;

public interface DonorService {
    DonorResponseDTO createDonor(CreateDonorDTO createDonorDTO) throws BadRequestException, ConflictException;
}
