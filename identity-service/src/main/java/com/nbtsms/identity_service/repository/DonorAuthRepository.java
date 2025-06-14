package com.nbtsms.identity_service.repository;

import com.nbtsms.identity_service.entity.DonorAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DonorAuthRepository extends JpaRepository<DonorAuth, UUID> {
    Optional<DonorAuth> findByPhoneNumber(String phoneNumber);
}
