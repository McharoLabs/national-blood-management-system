package com.nbts.management.donor_service.repository;

import com.nbts.management.donor_service.entity.Donor;
import com.nbts.management.donor_service.entity.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, UUID> {
    Optional<Questionnaire> findFirstByDonorOrderByFormStartedAtDesc(Donor donor);
}
