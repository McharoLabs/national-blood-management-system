package com.nbtsms.identity_service.repository;

import com.nbtsms.identity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
