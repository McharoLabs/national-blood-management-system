package com.management.nationalblood.laboratoryservice.repository;

import com.management.nationalblood.laboratoryservice.entity.LabTestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LabTestResultRepository extends JpaRepository<LabTestResult, UUID> {
}
