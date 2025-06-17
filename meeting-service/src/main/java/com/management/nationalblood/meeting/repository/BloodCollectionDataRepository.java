package com.management.nationalblood.meeting.repository;

import com.management.nationalblood.meeting.entity.BloodCollectionData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BloodCollectionDataRepository extends JpaRepository<BloodCollectionData, UUID> {
}
