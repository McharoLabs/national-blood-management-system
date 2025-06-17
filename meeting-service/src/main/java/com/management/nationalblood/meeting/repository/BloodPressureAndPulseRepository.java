package com.management.nationalblood.meeting.repository;

import com.management.nationalblood.meeting.entity.BloodPressureAndPulse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BloodPressureAndPulseRepository extends JpaRepository<BloodPressureAndPulse, UUID> {
}
