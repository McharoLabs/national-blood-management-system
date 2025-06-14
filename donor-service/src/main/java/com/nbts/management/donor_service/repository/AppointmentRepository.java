package com.nbts.management.donor_service.repository;

import com.nbts.management.donor_service.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    @Query("""
        SELECT a FROM Appointment a
        WHERE
            (:startDate IS NULL OR a.appointmentDate >= :startDate)
            AND (:endDate IS NULL OR a.appointmentDate <= :endDate)
            AND (LOWER(a.donor.fullName) LIKE LOWER(CONCAT('%', COALESCE(:fullName, ''), '%')))
    """)
    Page<Appointment> findAppointmentsByOptionalDonorNameAndAppointmentDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("fullName") String fullName,
            Pageable pageable
    );

    @Query("""
        SELECT a FROM Appointment a
        WHERE
            a.donor.id = :donorId
            AND (:appointmentDate IS NULL OR a.appointmentDate = :appointmentDate)
            AND (LOWER(a.donor.fullName) LIKE LOWER(CONCAT('%', COALESCE(:fullName, ''), '%')))
    """)
    Page<Appointment> findAppointmentsByOptionalDonorIdAndDonorNameAndAppointmentDate(
            @Param("appointmentDate") LocalDateTime appointmentDate,
            @Param("donorId") UUID donorId,
            @Param("fullName") String fullName,
            Pageable pageable
    );

    @Query("""
        SELECT a FROM Appointment a
        WHERE a.appointmentDate BETWEEN :startDate AND :endDate
    """)
    List<Appointment> findAppointmentsBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

}
