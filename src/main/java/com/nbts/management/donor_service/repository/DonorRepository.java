package com.nbts.management.donor_service.repository;

import com.nbts.management.donor_service.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DonorRepository extends JpaRepository<Donor, UUID> {
    Optional<Donor> findByFullNameIgnoreCase(String fullName);

    Optional<Donor> findByPhoneNumber(String phoneNumber);
}
