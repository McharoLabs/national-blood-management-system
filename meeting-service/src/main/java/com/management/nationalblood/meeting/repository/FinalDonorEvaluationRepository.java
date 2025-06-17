package com.management.nationalblood.meeting.repository;

import com.management.nationalblood.meeting.entity.FinalDonorEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FinalDonorEvaluationRepository extends JpaRepository<FinalDonorEvaluation, UUID> {
}
