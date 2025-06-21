package com.management.nationalblood.laboratoryservice.repository;

import com.management.nationalblood.laboratoryservice.entity.Assessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AssessmentRepository extends JpaRepository<Assessment, UUID> {
    boolean existsByQuestionnaireId(UUID questionnaireId);
    Optional<Assessment> findByQuestionnaireId(UUID questionnaireId);

    @Query("""
        SELECT a FROM Assessment a
        JOIN a.bloodCollectionData b
        WHERE a.centerId = :centerId
          AND a.labTestResult IS NULL
          AND (
            COALESCE(:bloodBagLotNumber, '') = '' OR
            LOWER(b.bloodBagLotNumber) LIKE LOWER(CONCAT('%', :bloodBagLotNumber, '%'))
          )
    """)
    Page<Assessment> findPendingAssessmentsByCenterAndOptionalBloodBagLotNumber(
            @Param("centerId") UUID centerId,
            @Param("bloodBagLotNumber") String bloodBagLotNumber,
            Pageable pageable
    );

    @Query("""
        SELECT a FROM Assessment a
        WHERE a.donorId = :donorId
          AND a.labTestResult IS NOT NULL
    """)
    Page<Assessment> findCompletedAssessmentsByDonorId(
            @Param("donorId") UUID donorId,
            Pageable pageable
    );

}
