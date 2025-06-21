package com.management.nationalblood.laboratoryservice.repository;

import com.management.nationalblood.laboratoryservice.entity.BloodCollectionData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BloodCollectionDataRepository extends JpaRepository<BloodCollectionData, UUID> {
}
