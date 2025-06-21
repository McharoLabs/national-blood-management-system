package com.management.nationalblood.laboratoryservice.repository;

import com.management.nationalblood.laboratoryservice.entity.HaematologicalTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HaematologicalTestRepository extends JpaRepository<HaematologicalTest, UUID> {
}
