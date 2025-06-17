package com.management.nationalblood.meeting.repository;

import com.management.nationalblood.meeting.entity.HaematologicalTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HaematologicalTestRepository extends JpaRepository<HaematologicalTest, UUID> {
}
