package com.nbtsms.identity_service.repository;

import com.nbtsms.identity_service.entity.User;
import com.nbtsms.identity_service.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRoles(Role role);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
