package com.nbts.management.donor_service.service;

import com.nbts.management.donor_service.dto.CreatePQDTO;
import com.nbts.management.donor_service.dto.QuestionnaireResponseDTO;
import com.nbts.management.donor_service.exception.ConflictException;
import com.nbts.management.donor_service.exception.NotFoundException;

public interface PQService {
    QuestionnaireResponseDTO createPQ(CreatePQDTO createPQDTO) throws NotFoundException, ConflictException;
}
