package com.nbtsms.identity_service.repository;

import com.nbtsms.identity_service.entity.User;
import com.nbtsms.identity_service.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRoles(Role role);

    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("""
            SELECT DISTINCT u FROM User u
            WHERE u.centerId = :centerId
            """)
    List<User> findCenterStaffByCenterId(@Param("centerId") UUID centerId);

    @Query("""
            SELECT DISTINCT u FROM User u
            WHERE
                LOWER(CONCAT(u.firstName, ' ', COALESCE(u.middleName, ''), ' ', u.lastName))
                    LIKE LOWER(CONCAT('%', COALESCE(:name, ''), '%'))
                AND (:includedRoles IS NULL OR EXISTS (
                        SELECT r FROM u.roles r WHERE r IN :includedRoles
                    ))
                AND NOT EXISTS (
                    SELECT r FROM u.roles r WHERE r = 'ADMIN' OR r = 'SUPER_USER'
                )
            """)
    Page<User> findAllStaffs(
            @Param("name") String name,
            @Param("includedRoles") List<Role> includedRoles,
            Pageable pageable
    );

    @Query("""
    SELECT DISTINCT u FROM User u
    WHERE
        LOWER(CONCAT(u.firstName, ' ', COALESCE(u.middleName, ''), ' ', u.lastName))
            LIKE LOWER(CONCAT('%', COALESCE(:name, ''), '%'))
        AND (
            :includedRoles IS NULL OR EXISTS (
                SELECT r FROM u.roles r WHERE r IN :includedRoles
            )
        )
    """)
    Page<User> findStaffsByRoles(
            @Param("name") String name,
            @Param("includedRoles") List<Role> includedRoles,
            Pageable pageable
    );

    @Query("""
        SELECT DISTINCT u FROM User u
        WHERE (
            LOWER(CONCAT(u.firstName, ' ', COALESCE(u.middleName, ''), ' ', u.lastName))
                LIKE LOWER(CONCAT('%', COALESCE(:name, ''), '%'))
            OR LOWER(u.email) LIKE LOWER(CONCAT('%', COALESCE(:email, ''), '%'))
        )
    """)
    Page<User> fetchSearchUsers(
            @Param("name") String name,
            @Param("email") String email,
            Pageable pageable
    );

    @Query("""
        SELECT u FROM User u
        JOIN u.roles r
        WHERE (
            LOWER(CONCAT(u.firstName, ' ', COALESCE(u.middleName, ''), ' ', u.lastName))
                LIKE LOWER(CONCAT('%', COALESCE(:name, ''), '%'))
            AND u.centerId IS NULL
            AND r IN ('COUNSELOR', 'ORGANIZER', 'LAB_TECHNICIAN')
            AND 'SUPER_USER' NOT IN (SELECT r2 FROM u.roles r2)
        )
    """)
    List<User> fetchAvailableStaff(@Param("name") String name);

    @Query("""
        SELECT u FROM User u
        JOIN u.roles r
        WHERE (
            LOWER(CONCAT(u.firstName, ' ', COALESCE(u.middleName, ''), ' ', u.lastName))
                LIKE LOWER(CONCAT('%', COALESCE(:name, ''), '%'))
            AND u.zoneId IS NULL
            AND r IN ('ADMIN', 'SUPER_USER')
        )
    """)
    List<User> fetchAvailableAdmin(@Param("name") String name);


}
