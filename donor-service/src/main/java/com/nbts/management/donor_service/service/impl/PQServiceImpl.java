package com.nbts.management.donor_service.service.impl;

import com.nbts.management.donor_service.dto.CreatePQDTO;
import com.nbts.management.donor_service.dto.QuestionnaireResponseDTO;
import com.nbts.management.donor_service.entity.Donor;
import com.nbts.management.donor_service.entity.Questionnaire;
import com.nbts.management.donor_service.exception.BadRequestException;
import com.nbts.management.donor_service.exception.ConflictException;
import com.nbts.management.donor_service.exception.NotFoundException;
import com.nbts.management.donor_service.mapper.PQMapper;
import com.nbts.management.donor_service.mapper.QuestionnaireMapper;
import com.nbts.management.donor_service.repository.DonorRepository;
import com.nbts.management.donor_service.repository.QuestionnaireRepository;
import com.nbts.management.donor_service.service.PQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PQServiceImpl implements PQService {
    private static final Logger logger = LoggerFactory.getLogger(PQServiceImpl.class);
    private final QuestionnaireRepository questionnaireRepository;
    private final DonorRepository donorRepository;

    public PQServiceImpl(QuestionnaireRepository questionnaireRepository, DonorRepository donorRepository) {
        this.questionnaireRepository = questionnaireRepository;
        this.donorRepository = donorRepository;
    }

    @Override
    public QuestionnaireResponseDTO createPQ(CreatePQDTO createPQDTO) throws NotFoundException, ConflictException {
        Map<String, String> errors = new HashMap<>();

        Donor donor = donorRepository.findById(createPQDTO.getDonorId()).orElse(null);

        if (donor == null) {
            errors.put("donorId", "Donor does not exist.");
            throw new NotFoundException(errors);
        }

        if (!canDonate(donor)) {
            errors.put("detail", "The donor cannot donate at this time because it has not yet been 3 years since the last donation.");
            throw new BadRequestException(errors);
        }

        try {
            Questionnaire questionnaire = PQMapper.toEntity(createPQDTO);
            questionnaire.setDonor(donor);

            Questionnaire insertedData = questionnaireRepository.save(questionnaire);

            return QuestionnaireMapper.toQuestionnaireDTO(insertedData);

        } catch (Exception e) {
            logger.error("An unexpected error occurred while inserting donor questionnaire.", e);
            throw new RuntimeException(e);
        }
    }


    public boolean canDonate(Donor donor) {
        Optional<Questionnaire> lastQuestionnaire = questionnaireRepository.findFirstByDonorOrderByFormStartedAtDesc(donor);

        return lastQuestionnaire.isEmpty() || lastQuestionnaire.get().isEligibleForNewDonation();
    }
}
