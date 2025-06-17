package com.management.nationalblood.meeting.repository;

import com.management.nationalblood.meeting.entity.PhysicalExamination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhysicalEvaluationRepository extends JpaRepository<PhysicalExamination, UUID> {
}
